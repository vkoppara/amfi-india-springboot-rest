package com.nir.mf.dailyupdates.bean;

import java.math.BigDecimal;
import java.util.Date;

public class Transaction {
	private Date date;
	private String transaction;
	private BigDecimal amount;
	private BigDecimal units;	
	private BigDecimal unitPrize;
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getTransaction() {
		return transaction;
	}
	public void setTransaction(String transaction) {
		this.transaction = transaction;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public BigDecimal getUnits() {
		return units;
	}
	public void setUnits(BigDecimal units) {
		this.units = units;
	}
	public BigDecimal getUnitPrize() {
		return unitPrize;
	}
	public void setUnitPrize(BigDecimal unitPrize) {
		this.unitPrize = unitPrize;
	}	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n\t"+this.date);
		sb.append(": ");
		sb.append(this.transaction);
		sb.append(": ");
		sb.append(this.amount);
		sb.append(": ");
		sb.append(this.units);
		sb.append(": ");
		sb.append(this.unitPrize);
		return sb.toString();
	}
}
