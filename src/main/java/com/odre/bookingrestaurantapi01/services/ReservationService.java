package com.odre.bookingrestaurantapi01.services;

import java.util.List;

import com.odre.bookingrestaurantapi01.exceptions.BookingException;
import com.odre.bookingrestaurantapi01.json.CreateReservationRest;
import com.odre.bookingrestaurantapi01.json.ReservationRest;

public interface ReservationService {
	
	public ReservationRest getReservationById(Long reservationId) throws BookingException;
	
	public String createReservation(CreateReservationRest createReservationRest) throws BookingException;
	
	public List<ReservationRest> Reservations() throws BookingException;
	
	public List<ReservationRest> getReservationByRestaurantId(Long restaurantId) throws BookingException;
	
	public void updateReservation(Boolean payment, String locator)throws BookingException;
	
}
