package com.nir.mf.dailyupdates.bean;

public class PdfRequest {
	private byte[] pdfStream;
	private byte[] password;
	private boolean doCalculate;

	public byte[] getPdfStream() {
		return pdfStream;
	}

	public void setPdfStream(byte[] pdfStream) {
		this.pdfStream = pdfStream;
	}

	public byte[] getPassword() {
		return password;
	}

	public void setPassword(byte[] password) {
		this.password = password;
	}

	public boolean isDoCalculate() {
		return doCalculate;
	}

	public void setDoCalculate(boolean doCalculate) {
		this.doCalculate = doCalculate;
	} 

}
