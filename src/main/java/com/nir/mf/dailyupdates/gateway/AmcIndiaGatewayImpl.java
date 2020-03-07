package com.nir.mf.dailyupdates.gateway;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import static org.springframework.http.HttpMethod.GET;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.nir.mf.dailyupdates.bean.NavObject;
import com.nir.mf.dailyupdates.bean.Rank;
import com.nir.mf.dailyupdates.bean.SearchResult;
import com.nir.mf.dailyupdates.config.Config;
import com.nir.mf.dailyupdates.exception.ExternalServiceException;
import com.nir.mf.dailyupdates.exception.RecordNotFoundException;
import com.nir.mf.dailyupdates.utility.Utility;

public class AmcIndiaGatewayImpl implements NavGateWay {

	private static final Logger logger = LogManager.getLogger(AmcIndiaGatewayImpl.class); 
	
	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private CacheManager cacheManager;


	@Value("${amf.url}")
	private String amfUrl;
	
	private String pattern = "dd-MMM-yyyy";
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
	
	

	@Override
	public NavObject getNavRecord(Integer schemeCode) throws RecordNotFoundException, ExternalServiceException {
		// TODO Auto-generated method stub
		NavObject navObject = getNavRecordFromCache(schemeCode);
		logger.info("getNavRecord for {} has been succesfully retrieved",schemeCode);
		if(navObject==null) {
			throw new RecordNotFoundException("Record not found: "+ schemeCode) ;
		}
		return navObject;
	}
	
	private Map<Integer, NavObject> getNavRecordsFromCache() throws ExternalServiceException {
		Map<Integer, NavObject> map=null;
		if(cacheManager.getCache("navAll").get("objects")!=null) {
			map = (HashMap<Integer, NavObject>) cacheManager.getCache("navAll").get("objects").get();
		}
		if (map != null) {
			return map;
		} else {
			logger.info("Pulling from external as the cache is empty or expired");
			map = this.pullFromExternal();
			cacheManager.getCache("navAll").put("objects", map);
			return map;
		}
	}

	private NavObject getNavRecordFromCache(Integer schemaCode) throws ExternalServiceException {
		
		return this.getNavRecordsFromCache().get(schemaCode);
		
	}

	public Map<Integer, NavObject> pullFromExternal() throws ExternalServiceException {
		Map<Integer, NavObject> map = null;
		try {
			ResponseEntity<String> responseEntity = restTemplate.exchange(new URI(amfUrl), GET, null, String.class);
			if (responseEntity.getBody() != null) {
				map = new HashMap<Integer,NavObject>();
				for (String str : responseEntity.getBody().split("\n")) {
					if (str.contains(";")) {
						String[] splits = str.split(";");
						if (Utility.isInteger(splits[0])) {
							   
								if(!Utility.isDecimal(splits[4])) 
								{
									splits[4]="0.00"; 
									
								}
								NavObject navObject = new NavObject(new Integer(splits[0]), splits[1], splits[2], splits[3],
										new BigDecimal(splits[4]), simpleDateFormat.parse(splits[5]));
								map.put(new Integer(splits[0]), navObject);
							
						}
					}
				}
			}
			return map;
		} catch (RestClientException | URISyntaxException | NumberFormatException | ParseException   e) {
			// TODO Auto-generated catch block
			logger.error("Pulling from external is failed: ",e);
			throw new ExternalServiceException("Pulling from external failed",e);


		}

	}
	
	@Override
	public SearchResult searchScheme( String filter) throws ExternalServiceException {
		SearchResult result=null;
		//Disable Cache for search..
		if(cacheManager.getCache("schemaSearch").get(filter)!=null && false) {
			result = (SearchResult) cacheManager.getCache("schemaSearch").get(filter).get();
		}
		if (result != null) {
			return result;
		} else {
			logger.info("Pulling from external as the cache is empty or expired");
			result = searchRawScheme(filter);
			cacheManager.getCache("schemaSearch").put(filter, result);
			return result;
		}
	}

	
	public SearchResult searchRawScheme(String filter) {
		// TODO Auto-generated method stub
		
				
		Set<SearchResult> sortedList = new TreeSet<SearchResult>();
		Map<Integer, NavObject> navObjects = this.getNavRecordsFromCache();
		for(Integer schemeCode: navObjects.keySet()) {
			if(navObjects.get(schemeCode).getSchemeName()!=null) {
				
					Rank rnk= matchRank(navObjects.get(schemeCode).getSchemeName(),filter);					
					SearchResult sr = new SearchResult(schemeCode,navObjects.get(schemeCode).getSchemeName());
					sr.setRank(rnk);
					sortedList.add(sr);
					
			}
		}
		
		
		int i=0;
		SearchResult result = new SearchResult();
		result.setRank(new Rank(0,0.0));
		for(SearchResult sr:sortedList) {
			if(i++>5) break;
			if(sr.getRank().getPercentage()>result.getRank().getPercentage())
				result = sr;
          //  System.out.println(sr.getSchemeName()+":"+sr.getRank().getPercentage());
			
			
		}
		
		return result;
	}
	
	
	
	private static Rank matchRank(String cacheString, String filterString) {
		filterString = filterString.toLowerCase().split("\\(")[0].replace("-"," ").replace(".","");
		String[] cacheArray = cacheString.replace("-", " ").replace(".", "").split(" ");
		Rank rnk = new Rank();
		int count=0;
		String[] filterArray = filterString.split(" ");
		if(!filterString.toLowerCase().contains(cacheArray[0].toLowerCase()))
		{
			rnk.setRank(0);
			rnk.setPercentage(0.0);
			return rnk;
		}
		
		for(String fstr:filterArray) {
			if(cacheString.toLowerCase().contains(fstr.toLowerCase())) {
				count++;
			}
		}
		
		
		int count2=0;
		for(String cac:cacheArray) {
			if(filterString.toLowerCase().contains(" "+cac.toLowerCase()+" ") ) {
				count2++;
			}
		
		}
		
		rnk.setRank(count);
		rnk.setPercentage(count2*100.0/cacheArray.length);
		return rnk;
		
		
	}
}
