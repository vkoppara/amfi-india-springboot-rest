package com.nir.mf.dailyupdates.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.nir.mf.dailyupdates.bean.ErrorResponse;

@ControllerAdvice
public class NavExceptionHandler  extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(RecordNotFoundException.class)
    public final ResponseEntity<ErrorResponse> handleRecordNotFoundException(Exception ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());              
        ErrorResponse error = new ErrorResponse("Record Not Found", details);        
        return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_FOUND);
    }
	
	@ExceptionHandler(ExternalServiceException.class)
	public final ResponseEntity<ErrorResponse> handleExternalServiceException(Exception ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());              
        ErrorResponse error = new ErrorResponse("Server Error", details);        
        return new ResponseEntity<ErrorResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
