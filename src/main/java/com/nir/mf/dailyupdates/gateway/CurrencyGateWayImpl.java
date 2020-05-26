package com.nir.mf.dailyupdates.gateway;

import static org.springframework.http.HttpMethod.GET;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.nir.mf.dailyupdates.bean.CurrencyResponse;
import com.nir.mf.dailyupdates.bean.NavObject;

public class CurrencyGateWayImpl implements CurrencyGateWay {
	
	private static final Logger logger = LogManager.getLogger(CurrencyGateWayImpl.class);

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private CacheManager cacheManager;
	
	@Value("${currency.url}")
	private String currencyUrl;
	
	@Override
	public BigDecimal convert() {
		// TODO Auto-generated method stub
		return this.retrieveFromCache();
	}
	
	private BigDecimal retrieveFromCache() {
		BigDecimal conversion = null;
		if(cacheManager.getCache("Currency").get("now")!=null) {
			conversion = (BigDecimal) cacheManager.getCache("Currency").get("now").get();
		}
		if(conversion!=null) {
			return conversion;
		} else {
			logger.info("Pulling Currency from external as the cache is empty or expired");
			try {
				ResponseEntity<CurrencyResponse> responseEntity = restTemplate.exchange(new URI(currencyUrl), GET, null, CurrencyResponse.class);
				conversion = responseEntity.getBody().getQuotes().getUSDINR();
				cacheManager.getCache("Currency").put("now", conversion);
			} catch (RestClientException | URISyntaxException e) {
				// TODO Auto-generated catch block
				logger.info("Exception occurred while calling the currencyURL",e);
			}
			
			return conversion;
		}
		
		
	}

}
