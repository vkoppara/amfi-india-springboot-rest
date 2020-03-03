package com.nir.mf.dailyupdates.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RecordNotFoundException extends RuntimeException  {
	
	public RecordNotFoundException(String message) {
		super(message);
	}
	public RecordNotFoundException() {
		super();
	}
	public RecordNotFoundException(String message, Throwable cause) {
		super(message,cause);
	}

}
