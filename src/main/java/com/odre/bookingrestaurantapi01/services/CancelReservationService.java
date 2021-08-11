package com.odre.bookingrestaurantapi01.services;

import com.odre.bookingrestaurantapi01.exceptions.BookingException;

public interface CancelReservationService {
	
	public String deleteReservation(String locator) throws BookingException;

}
