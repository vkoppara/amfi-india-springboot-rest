package com.nir.mf.dailyupdates.controller;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nir.mf.dailyupdates.bean.NavObject;
import com.nir.mf.dailyupdates.gateway.NavGateWay;

@RestController
public class MFController {

	private static final Logger logger = LogManager.getLogger(MFController.class);

	@Autowired
	private NavGateWay navGateway;

	@RequestMapping(value = "/getNav/{schemeCode}", method = RequestMethod.GET)
	public ResponseEntity<NavObject> getNav(@RequestHeader Map<String, String> requestHeaders,
			@PathVariable(value = "schemeCode") Integer schemeCode)  {
		logger.info("Recived request for retrieve NAV for {}",schemeCode);
		ResponseEntity<NavObject> responseEntity = new ResponseEntity<NavObject>(navGateway.getNavRecord(schemeCode),HttpStatus.OK);
		return  responseEntity;

	}
	

}
