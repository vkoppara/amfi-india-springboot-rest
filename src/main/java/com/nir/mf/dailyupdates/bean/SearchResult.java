package com.nir.mf.dailyupdates.bean;

public class SearchResult implements Comparable<SearchResult> {
	private Integer schemeCode;
	private String schemeName;
	private Rank rank;
	
	public SearchResult() {
		
	}
	public Rank getRank() {
		return rank;
	}
	public void setRank(Rank rank) {
		this.rank = rank;
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


	
	@Override
	public int compareTo(SearchResult sr1) {
		// TODO Auto-generated method stub
		if(this.getRank().getRank()>=sr1.getRank().getRank()) {
			return -1;
		}
		else return 1;
	}

}
