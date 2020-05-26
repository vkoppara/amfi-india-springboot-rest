package com.nir.mf.dailyupdates.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nir.mf.dailyupdates.bean.Funds;
import com.nir.mf.dailyupdates.bean.NavObject;
import com.nir.mf.dailyupdates.bean.PdfRequest;
import com.nir.mf.dailyupdates.bean.SearchResult;
import com.nir.mf.dailyupdates.businesslayer.CalculateRates;
import com.nir.mf.dailyupdates.gateway.CurrencyGateWay;
import com.nir.mf.dailyupdates.gateway.NavGateWay;
import com.nir.mf.dailyupdates.parser.PdfParser;

@RestController
public class MFController {

	private static final Logger logger = LogManager.getLogger(MFController.class);

	@Autowired
	private NavGateWay navGateway;
	
	@Autowired
	private PdfParser pdfParser;
	
	@Autowired
	private CalculateRates calculateRates;
	
	@Autowired
	private CurrencyGateWay currencyGateWay;

	@RequestMapping(value = "/getNav/{schemeCode}", method = RequestMethod.GET)
	public ResponseEntity<NavObject> getNav(@RequestHeader Map<String, String> requestHeaders,
			@PathVariable(value = "schemeCode") Integer schemeCode)  {
		logger.info("Recived request for retrieve NAV for {}",schemeCode);
		ResponseEntity<NavObject> responseEntity = new ResponseEntity<NavObject>(navGateway.getNavRecord(schemeCode),HttpStatus.OK);
		return  responseEntity;

	}
	
	@RequestMapping(value = "/searchScheme/{filter}",method = RequestMethod.GET)
	public ResponseEntity<SearchResult> searchScheme(@RequestHeader Map<String,String> requestHeaders,
			@PathVariable(value = "filter") String filter) {
		logger.info("Received request for searching scheme filter: {}",filter);
		ResponseEntity<SearchResult> responseEntity = new ResponseEntity<SearchResult>(navGateway.searchScheme(filter),HttpStatus.OK);
		return responseEntity;				
		
	}
	
	@RequestMapping(value = "/parseKarvyPdf", method = RequestMethod.POST)
	public ResponseEntity<List<Funds>> parseKarvyPdf(@RequestHeader Map<String,String> requestHeaders, 
			@RequestBody PdfRequest pdfRequest){
		List<Funds> parsedResult = pdfParser.parseKarvyPdf(pdfRequest.getPdfStream(),pdfRequest.getPassword());
		ResponseEntity<List<Funds>> responseEntity = new ResponseEntity<List<Funds>>(pdfRequest.isDoCalculate()?calculateRates.calculate(parsedResult):parsedResult,HttpStatus.OK);
		//ResponseEntity<List<Funds>> responseEntity = new ResponseEntity<List<Funds>>(parsedResult,HttpStatus.OK);
		return responseEntity;
	}
	
	@RequestMapping(value = "/calculateMyFunds", method = RequestMethod.POST)
	public ResponseEntity<List<Funds>> calculateMyFunds(@RequestHeader Map<String,String> requestHeaders, 
			@RequestBody List<Funds> funds){
		
		ResponseEntity<List<Funds>> responseEntity = new ResponseEntity<List<Funds>>(calculateRates.calculate(funds),HttpStatus.OK);
		return responseEntity;
	}
	
	@RequestMapping(value = "/convertUSD2INR", method = RequestMethod.GET)
	public ResponseEntity<BigDecimal> convertUsdToInr(@RequestHeader Map<String,String> requestHeaders) {
		
		ResponseEntity<BigDecimal> responseEntity = new ResponseEntity<BigDecimal>(currencyGateWay.convert(),HttpStatus.OK);
		return responseEntity;
	}

}
