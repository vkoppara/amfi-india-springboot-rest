package com.nir.mf.dailyupdates.bean;

public class CurrencyResponse {
	private String success;
	private String terms;
	private String privacy;
	private String timestamp;
	private String source;
	private Quotes quotes;
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	public String getTerms() {
		return terms;
	}
	public void setTerms(String terms) {
		this.terms = terms;
	}
	public String getPrivacy() {
		return privacy;
	}
	public void setPrivacy(String privacy) {
		this.privacy = privacy;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public Quotes getQuotes() {
		return quotes;
	}
	public void setQuotes(Quotes quotes) {
		this.quotes = quotes;
	}	

}
