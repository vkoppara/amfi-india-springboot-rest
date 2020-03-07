package com.nir.mf.dailyupdates.businesslayer;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.nir.mf.dailyupdates.bean.Funds;
import com.nir.mf.dailyupdates.bean.Transaction;
import com.nir.mf.dailyupdates.gateway.NavGateWay;

public class CalculateRates {
	
	@Autowired
	private NavGateWay navGateWay;

	public List<Funds> calculate(List<Funds> funds) {
		for(Funds f:funds) {
			Integer schemeCode = navGateWay.searchScheme(f.getFundName()).getSchemeCode();
			BigDecimal navValue = navGateWay.getNavRecord(schemeCode).getNetAssetValue();
			//System.out.println(f.getFundName()+":"+schemeCode+": "+navValue);
			BigDecimal closingUnitBalance = f.getClosingUnitBalance()==null?new BigDecimal("0.00"):f.getClosingUnitBalance();
			f.setCurrentValue(closingUnitBalance.multiply(navValue));
			BigDecimal totalAmount= new BigDecimal("0.00");
			for(Transaction tr:f.getTransactions()) {
				
				totalAmount= totalAmount.add(tr.getAmount());
			}
			//System.out.println(totalAmount);
			f.setInitialValue(totalAmount);
			f.setCurrentNav(navValue);
		}
		// TODO Auto-generated method stub
		return funds;
	}
	

}
