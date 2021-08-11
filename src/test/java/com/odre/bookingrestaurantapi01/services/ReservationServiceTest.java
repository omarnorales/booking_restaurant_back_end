package com.odre.bookingrestaurantapi01.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odre.bookingrestaurantapi01.entities.Reservation;
import com.odre.bookingrestaurantapi01.entities.Restaurant;
import com.odre.bookingrestaurantapi01.entities.Turn;
import com.odre.bookingrestaurantapi01.exceptions.BookingException;
import com.odre.bookingrestaurantapi01.json.CreateReservationRest;
import com.odre.bookingrestaurantapi01.repositories.ReservationRepository;
import com.odre.bookingrestaurantapi01.repositories.RestaurantRepository;
import com.odre.bookingrestaurantapi01.repositories.TurnRepository;
import com.odre.bookingrestaurantapi01.services.impl.ReservationServiceimpl;

public class ReservationServiceTest {

	final static Logger logger = LoggerFactory.getLogger(ReservationServiceTest.class);

	private static final Long RESERVATION_ID = 1L;

	private static final Reservation RESERVATION = new Reservation();

	private static final Optional<Reservation> OPTIONAL_RESERVATION = Optional.of(RESERVATION);

	private static final Optional<Reservation> OPTIONAL_RESERVATION_EMPTY = Optional.empty();

	private static final String LOCATOR = "Hamburger 6";
	private static final Long PERSON = 4L;
	private static final Date DATE = new Date();
	private static final Long TURN_ID = 1L;

	private static final Restaurant RESTAURANT = new Restaurant();

	private static final Optional<Restaurant> OPTIONAL_RESTAURANT = Optional.of(RESTAURANT);
	private static final Long RESTAURANT_ID = 1L;
	private static final String NAME = "Burger";
	private static final String DESCRIPTION = "All kind of burgers";
	private static final String ADDRESS = "burger joint, 119 W 56th St, New York, NY 10019";
	private static final String IMAGE = "https://irp-cdn.multiscreensite.com/b6050b0d/import/base/burgerandfries.jpg";

	private static final List<Turn> TURN_LIST = new ArrayList<>();

	private static final CreateReservationRest CREATE_RESERVATION_REST = new CreateReservationRest();

	private static final Turn TURN = new Turn();
	private static final Optional<Turn> OPTIONAL_TURN = Optional.of(TURN);
	private static final String TURN_NAME = "10:OO - 11:00";

	private static final Optional<Restaurant> OPTIONAL_RESTAURANT_EMPTY = Optional.empty();
	private static final Optional<Turn> OPTIONAL_TURN_EMPTY = Optional.empty();

	private static final List<Reservation> RESERVATION_LIST = Arrays.asList(RESERVATION);
	

	@Mock
	RestaurantRepository restaurantRepository;

	@Mock
	TurnRepository turnRepository;

	@Mock
	ReservationRepository reservationRepository;

	@InjectMocks
	ReservationServiceimpl reservationServiceimpl;

	@BeforeEach
	public void init() throws BookingException {

		MockitoAnnotations.openMocks(this);

		// Restaurant
		RESTAURANT.setId(RESTAURANT_ID);
		RESTAURANT.setName(NAME);
		RESTAURANT.setDescription(DESCRIPTION);
		RESTAURANT.setAddress(ADDRESS);
		RESTAURANT.setImage(IMAGE);
		RESTAURANT.setTurns(TURN_LIST);
//		RESTAURANT.setBoards(BOARD_LIST);
//		RESTAURANT.setReservations(RESERVATION_LIST);

		// Turn
		TURN.setId(TURN_ID);
		TURN.setName(TURN_NAME);
		TURN.setRestaurant(RESTAURANT);

		// reservation
		RESERVATION.setId(RESERVATION_ID);
		RESERVATION.setLocator(LOCATOR);
		RESERVATION.setPerson(PERSON);
		RESERVATION.setDate(DATE);
		// RESERVATION.setTurn(TURN_ID);

		CREATE_RESERVATION_REST.setDate(DATE);
		CREATE_RESERVATION_REST.setPerson(PERSON);
		CREATE_RESERVATION_REST.setRestaurantId(RESTAURANT_ID);
		CREATE_RESERVATION_REST.setTurnId(TURN_ID);
	}

	@Test
	public void createReservationTest() throws BookingException {
		Mockito.when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(OPTIONAL_RESTAURANT);
		Mockito.when(turnRepository.findById(TURN_ID)).thenReturn(OPTIONAL_TURN);
		Mockito.when(reservationRepository.findByTurnAndRestaurantId(TURN.getName(), RESTAURANT.getId()))
				.thenReturn(OPTIONAL_RESERVATION_EMPTY);		

		// every time we save or update
		Mockito.when(reservationRepository.save(Mockito.any(Reservation.class))).thenReturn(new Reservation());

		reservationServiceimpl.createReservation(CREATE_RESERVATION_REST);
	}

	@Test
	public void createReservationFindByIdTestError() throws BookingException {

		// expecting BookingException
		assertThrows(BookingException.class, () -> {

			Mockito.when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(OPTIONAL_RESTAURANT_EMPTY);
			

			reservationServiceimpl.createReservation(CREATE_RESERVATION_REST);
			fail();

		});

	}

	@Test
	public void createReservationTurnFindByIdTestError() throws BookingException {

		assertThrows(BookingException.class, () -> {

			Mockito.when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(OPTIONAL_RESTAURANT);
			Mockito.when(turnRepository.findById(TURN_ID)).thenReturn(OPTIONAL_TURN_EMPTY);

			reservationServiceimpl.createReservation(CREATE_RESERVATION_REST);
			fail();

		});

	}

	@Test
	public void createReservationTurnFindByTurnAndRestaurantIdTestError() throws BookingException {

		assertThrows(BookingException.class, () -> {

			Mockito.when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(OPTIONAL_RESTAURANT);
			Mockito.when(turnRepository.findById(TURN_ID)).thenReturn(OPTIONAL_TURN);
			Mockito.when(reservationRepository.findByTurnAndRestaurantId(TURN.getName(), RESTAURANT.getId()))
					.thenReturn(OPTIONAL_RESERVATION);

			reservationServiceimpl.createReservation(CREATE_RESERVATION_REST);
			fail();

		});

	}

	@Test
	public void createReservationInternalServerErrorTest() throws BookingException {

		assertThrows(BookingException.class, () -> {
			Mockito.when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(OPTIONAL_RESTAURANT);
			Mockito.when(turnRepository.findById(TURN_ID)).thenReturn(OPTIONAL_TURN);
			Mockito.when(reservationRepository.findByTurnAndRestaurantId(TURN.getName(), RESTAURANT.getId()))
					.thenReturn(OPTIONAL_RESERVATION_EMPTY);

			Mockito.doThrow(new RuntimeException()).when(reservationRepository).save(Mockito.any(Reservation.class));
			logger.info("Starting... createReservationInternalServerErrorTest() --> Fail() is expencted");
			reservationServiceimpl.createReservation(CREATE_RESERVATION_REST);
			fail();
		});

		logger.info("Completed... createReservationInternalServerErrorTest() --> Fail() is expencted");

	}

	@Test
	public void getReservationByIdTest() throws BookingException {

		Mockito.when(reservationRepository.findById(RESERVATION_ID)).thenReturn(OPTIONAL_RESERVATION);
		reservationServiceimpl.getReservationById(RESERVATION_ID);
	}

	@Test
	public void getReservationByRestaurantIdTest() throws BookingException {

		Mockito.when(reservationRepository.queryFindByRestaurantId(RESTAURANT_ID)).thenReturn(Arrays.asList(RESERVATION));
		reservationServiceimpl.getReservationByRestaurantId(RESTAURANT_ID);

	}

	@Test
	public void ReservationsTest() throws BookingException {

		Mockito.when(reservationRepository.findAll()).thenReturn(RESERVATION_LIST);
		reservationServiceimpl.Reservations();

	}

}
