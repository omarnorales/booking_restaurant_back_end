package com.odre.bookingrestaurantapi01.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.odre.bookingrestaurantapi01.entities.Board;
import com.odre.bookingrestaurantapi01.entities.Reservation;
import com.odre.bookingrestaurantapi01.entities.Restaurant;
import com.odre.bookingrestaurantapi01.entities.Turn;
import com.odre.bookingrestaurantapi01.exceptions.BookingException;
import com.odre.bookingrestaurantapi01.json.RestaurantRest;
import com.odre.bookingrestaurantapi01.repositories.RestaurantRepository;
import com.odre.bookingrestaurantapi01.services.impl.RestaurantServiceImpl;

public class RestaurantServiceTest {

	private static final Long RESTAURANT_ID = 1L;

	private static final Restaurant RESTAURANT = new Restaurant();

	private static final String NAME = "Burger";
	private static final String DESCRIPTION = "All kind of burgers";
	private static final String ADDRESS = "burger joint, 119 W 56th St, New York, NY 10019";
	private static final String IMAGE = "https://irp-cdn.multiscreensite.com/b6050b0d/import/base/burgerandfries.jpg";

	private static final List<Turn> TURN_LIST = new ArrayList<>();
	private static final List<Board> BOARD_LIST = new ArrayList<>();
	private static final List<Reservation> RESERVATION_LIST = new ArrayList<>();


	@Mock
	RestaurantRepository restaurantRepository;

	@InjectMocks
	RestaurantServiceImpl restaurantServiceImpl;

	@BeforeEach
	public void init() throws BookingException {
		MockitoAnnotations.openMocks(this);

		RESTAURANT.setId(RESTAURANT_ID);
		RESTAURANT.setName(NAME);
		RESTAURANT.setDescription(DESCRIPTION);
		RESTAURANT.setAddress(ADDRESS);
		RESTAURANT.setImage(IMAGE);
		RESTAURANT.setTurns(TURN_LIST);
		RESTAURANT.setBoards(BOARD_LIST);
		RESTAURANT.setReservations(RESERVATION_LIST);

	}

	@Test
	public void getRestaurantByIdTest() throws BookingException {

		Mockito.when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(Optional.of(RESTAURANT));
		restaurantServiceImpl.getRestaurantById(RESTAURANT_ID);
	}

	@Test
	public void getRestaurantByIdTestError() throws BookingException {

		// Exception exception =
		assertThrows(BookingException.class, () -> {
			Mockito.when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(Optional.empty());
			restaurantServiceImpl.getRestaurantById(RESTAURANT_ID);
			fail();
		});

		// assertEquals("/ by zero", exception.getMessage());
		// assertTrue(exception.getMessage().contains("zero"));
	}

	@Test
	public void getRestaurantsTest() throws BookingException {

		Mockito.when(restaurantRepository.findAll()).thenReturn(Arrays.asList(RESTAURANT));
		final List<RestaurantRest> response = restaurantServiceImpl.getRestaurants();

		// assert that actuall is not null
		assertNotNull(response);

		// assert that condition is false
		assertFalse(response.isEmpty());

		assertEquals(response.size(), 1);

	}

}
