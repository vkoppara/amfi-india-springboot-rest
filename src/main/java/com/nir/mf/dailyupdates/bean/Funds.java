package com.nir.mf.dailyupdates.bean;

import java.math.BigDecimal;
import java.util.List;



public class Funds {
	private String fundName;
	private String folioNumber;
	private String panNumber;
	private List<Transaction> transactions;
	private BigDecimal closingUnitBalance;
	private BigDecimal currentValue;
	private BigDecimal currentNav;
	public BigDecimal getCurrentNav() {
		return currentNav;
	}
	public void setCurrentNav(BigDecimal currentNav) {
		this.currentNav = currentNav;
	}

	private BigDecimal initialValue;
	
	public BigDecimal getCurrentValue() {
		return currentValue;
	}
	public void setCurrentValue(BigDecimal currentValue) {
		this.currentValue = currentValue;
	}
	public BigDecimal getInitialValue() {
		return initialValue;
	}
	public void setInitialValue(BigDecimal initialValue) {
		this.initialValue = initialValue;
	}
	public BigDecimal getClosingUnitBalance() {
		return closingUnitBalance;
	}
	public void setClosingUnitBalance(BigDecimal closingUnitBalance) {
		this.closingUnitBalance = closingUnitBalance;
	}
	public String getFundName() {
		return fundName;
	}
	public void setFundName(String fundName) {
		this.fundName = fundName;
	}
	public String getFolioNumber() {
		return folioNumber;
	}
	public void setFolioNumber(String folioNumber) {
		this.folioNumber = folioNumber;
	}
	public String getPanNumber() {
		return panNumber;
	}
	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}
	public List<Transaction> getTransactions() {
		return transactions;
	}
	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Fund Name: "+this.fundName);
		sb.append(this.transactions!=null?this.transactions.toString():null);
		
		BigDecimal sum=new BigDecimal("0.00");
		for(Transaction tr:this.transactions) {
			sum = sum.add(tr.getUnits());
		}
		sb.append("\nClosing Unit Balance:"+this.getClosingUnitBalance()+ " Calculated:"+sum);
		return sb.toString();
		
		
	}
}
