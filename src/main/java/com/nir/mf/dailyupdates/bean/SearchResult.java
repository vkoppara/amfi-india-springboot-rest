package com.nir.mf.dailyupdates.bean;

import java.util.Map;

public class SearchResult {
	private Integer schemeCode;
	private String schemeName;
	
	public SearchResult() {
		
	}
	public SearchResult(Integer schemeCode, String schemeName) {
		this.schemeCode = schemeCode;
		this.schemeName = schemeName;
		// TODO Auto-generated constructor stub
	}
	public Integer getSchemeCode() {
		return schemeCode;
	}
	public void setSchemeCode(Integer schemeCode) {
		this.schemeCode = schemeCode;
	}
	public String getSchemeName() {
		return schemeName;
	}
	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}	

}
