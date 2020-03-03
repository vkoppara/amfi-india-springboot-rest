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

}
