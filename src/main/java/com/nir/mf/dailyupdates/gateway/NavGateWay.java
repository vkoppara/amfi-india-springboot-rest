package com.nir.mf.dailyupdates.gateway;

import java.util.List;
import java.util.Map;

import com.nir.mf.dailyupdates.bean.NavObject;
import com.nir.mf.dailyupdates.bean.SearchResult;
import com.nir.mf.dailyupdates.exception.ExternalServiceException;
import com.nir.mf.dailyupdates.exception.RecordNotFoundException;

public interface NavGateWay {

	public NavObject getNavRecord(Integer schemeCode) throws RecordNotFoundException, ExternalServiceException;
	public Map<Integer, NavObject> pullFromExternal() throws ExternalServiceException;
	public SearchResult searchScheme(String filter);
}
