package com.nir.mf.dailyupdates.gateway;

import java.util.HashMap;

import com.nir.mf.dailyupdates.bean.NavObject;
import com.nir.mf.dailyupdates.exception.ExternalServiceException;
import com.nir.mf.dailyupdates.exception.RecordNotFoundException;

public interface NavGateWay {

	public NavObject getNavRecord(Integer schemeCode) throws RecordNotFoundException, ExternalServiceException;
	public HashMap<Integer, NavObject> pullFromExternal() throws ExternalServiceException;
}
