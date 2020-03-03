package com.nir.mf.dailyupdates.utility;

public class Utility {
	
	public static Boolean isInteger(String str) {
		try {
			Integer.parseInt(str);
			return true;
		}catch(NumberFormatException e) {
			return false;
		}catch(Exception e) {
			return null;
		}
		
	}

	public static Boolean isDecimal(String str) {
		// TODO Auto-generated method stub
		try {
			Double.parseDouble(str);
			return true;
		
		}catch(NumberFormatException e) {
			return false;
		}catch(Exception e) {
			return null;
		}
	}

}
