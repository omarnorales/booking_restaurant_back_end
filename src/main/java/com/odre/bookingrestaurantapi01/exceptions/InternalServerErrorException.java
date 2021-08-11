package com.odre.bookingrestaurantapi01.exceptions;

import java.util.Arrays;

import org.springframework.http.HttpStatus;

import com.odre.bookingrestaurantapi01.dtos.ErrorDto;

public class InternalServerErrorException extends BookingException {

	
	private static final long serialVersionUID = 1L;

	public InternalServerErrorException(String code, String message) {
		super(code, HttpStatus.INTERNAL_SERVER_ERROR.value() ,message);
		
	}
	
	public InternalServerErrorException(String code, String message, ErrorDto data) {
		super(code, HttpStatus.INTERNAL_SERVER_ERROR.value() ,message, Arrays.asList(data));
		
	}

}
