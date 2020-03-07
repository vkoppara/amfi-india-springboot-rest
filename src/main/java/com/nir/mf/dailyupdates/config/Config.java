package com.nir.mf.dailyupdates.config;



import java.util.concurrent.TimeUnit;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.cache2k.event.CacheEntryExpiredListener;
import org.cache2k.extra.spring.SpringCache2kCacheManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.nir.mf.dailyupdates.businesslayer.CalculateRates;
import com.nir.mf.dailyupdates.exception.ExternalServiceException;
import com.nir.mf.dailyupdates.gateway.AmcIndiaGatewayImpl;
import com.nir.mf.dailyupdates.gateway.NavGateWay;
import com.nir.mf.dailyupdates.parser.KarvyPdfParser;
import com.nir.mf.dailyupdates.parser.PdfParser;





@Configuration
@EnableCaching
public class Config {
	
	private static final Logger logger = LogManager.getLogger(Config.class); 
	
	@Value("${caching.interval}")
	private int cachingInterval;
	
	@Bean
	public RestTemplate getRestTemplate() {
		HttpClient httpClient = HttpClientBuilder.create()
				.useSystemProperties()
				.setSSLHostnameVerifier(new NoopHostnameVerifier())
				.disableCookieManagement()
				.disableAuthCaching()
				.build();
		HttpComponentsClientHttpRequestFactory requestFactory = 
				new HttpComponentsClientHttpRequestFactory(httpClient);
		requestFactory.setReadTimeout(10000);
		logger.debug("RestTemplate has been created");
		return new RestTemplate(requestFactory);
	}
	
	@Bean
	public NavGateWay retrieveNav() {
		return new AmcIndiaGatewayImpl();
	}
	
	@Bean
	public PdfParser retrievePdfParser() {
		return new KarvyPdfParser();
	}

	@Bean
	public CalculateRates getCalculateRates() {
		return new CalculateRates();
	}
	
	@Bean
	public CacheManager cacheManager(NavGateWay gateway) {
		
		CacheEntryExpiredListener listener = (cache,entry) ->
		{
			logger.info("pulling from external");
			try {
				cache.put(entry.getKey(), gateway.pullFromExternal());
			} catch (ExternalServiceException e) {
				// TODO Auto-generated catch block
				logger.error("pulling from external",e);
			}
			
		};
		
		return new SpringCache2kCacheManager()
				.defaultSetup(b->b.entryCapacity(1000))
				.addCaches(
						b -> b.name("navAll").expireAfterWrite(cachingInterval, TimeUnit.SECONDS)
						.addListener(listener))
				.addCaches(b -> b.name("schemaSearch"));		
	}
	
	

}
