package com.odre.bookingrestaurantapi01.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.odre.bookingrestaurantapi01.controllers.RestaurantController;
import com.odre.bookingrestaurantapi01.exceptions.BookingException;
import com.odre.bookingrestaurantapi01.json.RestaurantRest;
import com.odre.bookingrestaurantapi01.json.TurnRest;
import com.odre.bookingrestaurantapi01.responses.BookingResponse;
import com.odre.bookingrestaurantapi01.services.RestaurantService;

public class RestaurantControllerTest {

	private static final Long RESTAURANT_ID = 1L;

	private static final String SUCCESS_STATUS = "Success";
	private static final String SUCCESS_CODE = "200 OK";
	private static final String OK = "OK";

	private static final RestaurantRest RESTAURANT_REST = new RestaurantRest();
	private static final String NAME = "Burger";
	private static final String DESCRIPTION = "All kind of burgers";
	private static final String ADDRESS = "burger joint, 119 W 56th St, New York, NY 10019";
	private static final String IMAGE = "https://irp-cdn.multiscreensite.com/b6050b0d/import/base/burgerandfries.jpg";

	private static final List<TurnRest> TURN_LIST = new ArrayList<>();
	
	private static final List<RestaurantRest> RESTAURANT_REST_LIST = new ArrayList<>();

	@Mock
	RestaurantService restaurantService;

	@InjectMocks
	RestaurantController restaurantController;

	@BeforeEach
	public void init() throws BookingException {
		MockitoAnnotations.openMocks(this);

		RESTAURANT_REST.setId(RESTAURANT_ID);
		RESTAURANT_REST.setName(NAME);
		RESTAURANT_REST.setDescription(DESCRIPTION);
		RESTAURANT_REST.setAddress(ADDRESS);
		RESTAURANT_REST.setImage(IMAGE);
		RESTAURANT_REST.setTurns(TURN_LIST);

		Mockito.when(restaurantService.getRestaurantById(RESTAURANT_ID)).thenReturn(RESTAURANT_REST);

	}

	@Test
	public void getRestaurantByIdTest() throws BookingException {
		final BookingResponse<RestaurantRest> response = restaurantController.getRestaurantById(RESTAURANT_ID);

		assertEquals(response.getStatus(), SUCCESS_STATUS);
		assertEquals(response.getCode(), SUCCESS_CODE);
		assertEquals(response.getMessage(), OK);
		assertEquals(response.getData(), RESTAURANT_REST);
	}
	
	@Test
	public void getRestaurantsTest() throws BookingException{
		final BookingResponse<List<RestaurantRest>> response = restaurantController.getRestaurants();
		
		assertEquals(response.getStatus(), SUCCESS_STATUS);
		assertEquals(response.getCode(), SUCCESS_CODE);
		assertEquals(response.getMessage(), OK);
		assertEquals(response.getData(), RESTAURANT_REST_LIST);
		
		
	}

}
