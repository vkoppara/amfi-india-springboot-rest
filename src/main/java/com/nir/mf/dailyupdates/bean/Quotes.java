package com.nir.mf.dailyupdates.bean;

import java.math.BigDecimal;

public class Quotes {
	public int USDUSD;
	public BigDecimal USDINR;
	
	public int getUSDUSD() {
		return USDUSD;
	}
	public void setUSDUSD(int uSDUSD) {
		this.USDUSD = uSDUSD;
	}
	public BigDecimal getUSDINR() {
		return USDINR;
	}
	public void setUSDINR(BigDecimal uSDINR) {
		this.USDINR = uSDINR;
	}

}
