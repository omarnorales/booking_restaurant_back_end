package com.odre.bookingrestaurantapi01.exceptions;

import java.util.ArrayList;
import java.util.List;

import com.odre.bookingrestaurantapi01.dtos.ErrorDto;

public class BookingException extends Exception {
	

	private static final long serialVersionUID = 1L;

	private final String code;
	
	private final int responseCode;
	
	private final List<ErrorDto> errorList = new ArrayList<>();
	
	public String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public BookingException(String code, int responseCode, String message) {
		//super(message);
		this.code = code;
		this.responseCode = responseCode;
		this.message = message;
	}
	
	public BookingException(String code, int responseCode, String message, List<ErrorDto> errorLis) {
		//super(message);
		this.code = code;
		this.responseCode = responseCode;
		this.errorList.addAll(errorLis);
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public List<ErrorDto> getErrorList() {
		return errorList;
	}

}
