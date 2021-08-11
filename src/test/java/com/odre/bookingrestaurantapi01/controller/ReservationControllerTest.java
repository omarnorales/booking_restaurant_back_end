package com.odre.bookingrestaurantapi01.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.odre.bookingrestaurantapi01.controllers.ReservationController;
import com.odre.bookingrestaurantapi01.exceptions.BookingException;
import com.odre.bookingrestaurantapi01.json.CreateReservationRest;
import com.odre.bookingrestaurantapi01.json.ReservationRest;
import com.odre.bookingrestaurantapi01.responses.BookingResponse;
import com.odre.bookingrestaurantapi01.services.ReservationService;

public class ReservationControllerTest {

	private static final Long RESERVATION_ID = 1L;

	private static final String SUCCESS_STATUS = "Success";
	private static final String SUCCESS_CODE = "200 OK";
	private static final String OK = "OK";

	private static final ReservationRest RESERVATION_REST = new ReservationRest();
	private static final String LOCATOR = "Hamburger 6";
	private static final Long PERSON = 4L;
	private static final Date DATE = new Date();
	private static final Long TURN_ID = 1L;

	private static final CreateReservationRest CREATE_RESERVATION_REST = new CreateReservationRest();

	@Mock
	ReservationService reservationService;

	@InjectMocks
	ReservationController reservationController;

	@BeforeEach
	public void init() throws BookingException {
		MockitoAnnotations.openMocks(this);

		RESERVATION_REST.setId(RESERVATION_ID);
		RESERVATION_REST.setLocator(LOCATOR);
		RESERVATION_REST.setPerson(PERSON);
		RESERVATION_REST.setDate(DATE);
		RESERVATION_REST.setTurnId(TURN_ID);

		Mockito.when(reservationService.getReservationById(RESERVATION_ID)).thenReturn(RESERVATION_REST);

		Mockito.when(reservationService.createReservation(CREATE_RESERVATION_REST)).thenReturn(LOCATOR);

	}

	@Test
	public void getReservationByIdTest() throws BookingException {
		final BookingResponse<ReservationRest> response = reservationController.getReservationById(RESERVATION_ID);

		assertEquals(response.getStatus(), SUCCESS_STATUS);
		assertEquals(response.getCode(), SUCCESS_CODE);
		assertEquals(response.getMessage(), OK);
		assertEquals(response.getData(), RESERVATION_REST);

	}

	@Test
	public void createReservationTest() throws BookingException {
		final BookingResponse<String> response = reservationController.createReservation(CREATE_RESERVATION_REST);

		assertEquals(response.getStatus(), SUCCESS_STATUS);
		assertEquals(response.getCode(), SUCCESS_CODE);
		assertEquals(response.getMessage(), OK);
		assertEquals(response.getData(), LOCATOR);

	}

}
