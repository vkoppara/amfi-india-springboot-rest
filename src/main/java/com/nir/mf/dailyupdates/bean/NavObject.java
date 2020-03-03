package com.nir.mf.dailyupdates.bean;

import java.math.BigDecimal;
import java.util.Date;

public class NavObject {
	private Integer schemeCode;
	private String isinDivPayoutGrowth;
	private String isinDivReinvestment;
	private String schemeName;
	private BigDecimal netAssetValue;
	private Date date;

	public NavObject() {
		super();
	}
	public NavObject(Integer schemeCode,String isinDivPayoutGrowth, String isinDivReinvestment,
			String schemeName, BigDecimal netAssetValue, Date date) {
		this.schemeCode=schemeCode;
		this.isinDivPayoutGrowth=isinDivPayoutGrowth;
		this.isinDivReinvestment=isinDivReinvestment;
		this.schemeName=schemeName;
		this.netAssetValue=netAssetValue;
		this.date=date;
	}
	
	public Integer getSchemeCode() {
		return schemeCode;
	}
	public void setSchemeCode(Integer schemeCode) {
		this.schemeCode = schemeCode;
	}
	public String getIsinDivPayoutGrowth() {
		return isinDivPayoutGrowth;
	}
	public void setIsinDivPayoutGrowth(String isinDivPayoutGrowth) {
		this.isinDivPayoutGrowth = isinDivPayoutGrowth;
	}
	public String getIsinDivReinvestment() {
		return isinDivReinvestment;
	}
	public void setIsinDivReinvestment(String isinDivReinvestment) {
		this.isinDivReinvestment = isinDivReinvestment;
	}
	public String getSchemeName() {
		return schemeName;
	}
	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}
	public BigDecimal getNetAssetValue() {
		return netAssetValue;
	}
	public void setNetAssetValue(BigDecimal netAssetValue) {
		this.netAssetValue = netAssetValue;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

	
}
