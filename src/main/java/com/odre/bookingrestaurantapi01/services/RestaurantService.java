package com.odre.bookingrestaurantapi01.services;

import java.util.List;

import com.odre.bookingrestaurantapi01.exceptions.BookingException;
import com.odre.bookingrestaurantapi01.json.RestaurantRest;


public interface RestaurantService {
	
	/*
	 * Aqui la logica de negocios
	 */
	
	RestaurantRest getRestaurantById(Long restaurantId) throws BookingException;
	
	public List<RestaurantRest> getRestaurants() throws BookingException;
	

}
