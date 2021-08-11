package com.odre.bookingrestaurantapi01.services;

import com.odre.bookingrestaurantapi01.exceptions.BookingException;

public interface EmailService {

	public String processSendEmail(final String receiver, String templateCode, String currentName)
			throws BookingException;

}
