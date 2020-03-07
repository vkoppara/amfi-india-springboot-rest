package com.nir.mf.dailyupdates.parser;

import java.util.List;

import com.nir.mf.dailyupdates.bean.Funds;

public interface PdfParser {

	public List<Funds> parseKarvyPdf(byte[] stream, byte[] password);

}
