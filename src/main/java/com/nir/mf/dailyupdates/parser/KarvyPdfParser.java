package com.nir.mf.dailyupdates.parser;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.nir.mf.dailyupdates.bean.Funds;
import com.nir.mf.dailyupdates.bean.Transaction;



public class KarvyPdfParser implements PdfParser {
	private static SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");

	@Override
	public List<Funds> parseKarvyPdf(byte[] stream, byte[] password) {
		ArrayList<Funds> funds = new ArrayList<Funds>();
		 try {
				PdfReader pdfReader = new PdfReader(stream,password);
				StringBuffer sb = new StringBuffer();
				for(int i=0;i<pdfReader.getNumberOfPages();i++)
				sb.append(PdfTextExtractor.getTextFromPage(pdfReader, 1+i));
				List<String> list = retrieveFoliosFunds(sb.toString());
				
				for(String strLine:list) {
					
						Funds f=getIndivdualFunds(strLine);
						funds.add(f);
					
				}
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		return funds;
	}
	
    private static List<String> retrieveFoliosFunds(String str) {
    	List<String> resultList = new ArrayList<String>();
    	Boolean isBegin = false;
    	StringBuffer folioChunk = new StringBuffer();
    	for(String strLine:str.split("\n")) {
    		if(strLine.contains("Folio No")) {
    			isBegin = true;    			
    			
    		}
    		if(strLine.contains("Closing Unit Balance")) {
				isBegin = false;
				folioChunk.append(strLine);
				resultList.add(folioChunk.toString());
				folioChunk = new StringBuffer();
			}
    		if(isBegin) {
    			folioChunk.append(strLine +"\n");
    		}
    		
    	}
    	return resultList;
    	
    }
    
    private static Funds getIndivdualFunds(String str) {
    	Funds fund = new Funds(); 
    	List<Transaction> transactions = new ArrayList<Transaction>();
    	fund.setTransactions(transactions);
    	boolean isStart = false;
    	boolean isNameNextLine = false;
    	String twoLines = null;
    	for(String strLine:str.split("\n")) {
    		strLine=strLine.trim();
    		if(strLine.contains("Folio No")) {
    			fund.setPanNumber(strLine.split("PAN: ")[1].split(" ")[0]);    			
    			fund.setFolioNumber(strLine.split("PAN: ")[0].split("Folio No : ")[1]);
    		}
    		if(strLine.contains("Registrar")) {
    			fund.setFundName(strLine.split("Registrar")[0]);    			
    		}
    		if(strLine.contains("Closing Unit Balance")) {
     			fund.setClosingUnitBalance(new BigDecimal(strLine.replace(",","").split("Closing Unit Balance: ")[1].split(" ")[0]));
    		}
    		
    		if(isStart && !strLine.equals("")) {
     			isStart = false;
    			String[] beforeLine = twoLines.replace(",", "").split(" ");
    			Transaction transaction = new Transaction();
				try {
					String[] splits = strLine.replace(",", "").split(" ");
					transaction.setDate(returnDate(beforeLine[0]));
					transaction.setAmount(new BigDecimal(returnWithSign(splits[splits.length-2])));
					transaction.setUnits(new BigDecimal(returnWithSign(beforeLine[beforeLine.length-2])));
					transaction.setUnitPrize(new BigDecimal(beforeLine[beforeLine.length-1]));
					transaction.setTransaction(buildTransactionName(strLine.split(" "),0,2));
					transactions.add(transaction);
				}catch(NumberFormatException e) {
					continue;
				}
		
    		}
    		if(isNameNextLine && !strLine.equals("")) {
    			isNameNextLine=false;
				Transaction transaction = new Transaction();
				try {
					String[] splits = twoLines.replace(",", "").split(" ");
					transaction.setDate(returnDate(splits[0]));    					
					transaction.setAmount(new BigDecimal(returnWithSign(splits[splits.length-4])));
					transaction.setUnits(new BigDecimal(returnWithSign(splits[splits.length-3])));
					transaction.setUnitPrize(new BigDecimal(splits[splits.length-2]));
					String beforeName=buildTransactionName(splits,1,4);
					String currentName= buildTransactionName(strLine.split(" "),0,0);
					transaction.setTransaction(beforeName.trim().equals("")?currentName:beforeName);					
					transactions.add(transaction);
					
				}catch(NumberFormatException e) {
					System.out.println("Exception:"+strLine);
					continue;
				}
    			
    		}
    		
    		if(strLine.length()>0 && !strLine.contains("*") && strLine.split(" ").length>1 && returnDate(strLine.split(" ")[0])!=null) {
    			
    			if(strLine.trim().split(" ").length<5) {
    				isStart = true;
    				twoLines=strLine;
    			}
    			else {
    				twoLines=strLine;    				
    				isNameNextLine = true;
    			}
    			    			
    		}
    		    		
    	}    	
    	return fund;
    }
    private static String returnWithSign(String str) {
    	String returnedString=str;
    	if(str.contains("(") && str.contains(")")) {
    		returnedString= "-"+str.substring(1,str.length()-1);
    	}    	
    	return returnedString.contains(".")?returnedString:returnedString+".00";
    		
    }
    private static String buildTransactionName(String[] splits,int start, int end) {
    	StringBuilder sb = new StringBuilder();
    	for(int i=start;i<splits.length-end;i++) {
    		sb.append(splits[i]+ " ");
    	}
    	return sb.toString();
    }
    private static Date returnDate(String dt) {
    	try {
    		return format.parse(dt);
    	}catch(Exception e) {
    		return null;
    	}
    }
}
