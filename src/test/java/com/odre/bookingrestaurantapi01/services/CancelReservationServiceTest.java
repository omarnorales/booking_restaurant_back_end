package com.odre.bookingrestaurantapi01.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.odre.bookingrestaurantapi01.entities.Reservation;
import com.odre.bookingrestaurantapi01.exceptions.BookingException;
import com.odre.bookingrestaurantapi01.repositories.ReservationRepository;
import com.odre.bookingrestaurantapi01.services.impl.CancelReservationServiceImpl;

public class CancelReservationServiceTest {
	

	
	private static final Reservation RESERVATION = new Reservation();

	private static final Optional<Reservation> OPTIONAL_RESERVATION = Optional.of(RESERVATION);
	
	private static final Optional<Reservation> OPTIONAL_RESERVATION_EMPTY = Optional.empty();



	private static final Long RESERVATION_ID = 1L;
	private static final String LOCATOR = "Hamburger 6";
	private static final Long PERSON = 4L;
	private static final Date DATE = new Date();

	private static final String TURN_NAME_ = "Totos Burger";
	
	private static final String RESERVATION_DELETED = "LOCATOR_DELETED";
	
	@Mock
	ReservationRepository reservationRepository;
	
	@InjectMocks
	CancelReservationServiceImpl cancelReservationServiceImpl;
	
	@BeforeEach
	public void init() throws BookingException {
		MockitoAnnotations.openMocks(this);
		
		RESERVATION.setId(RESERVATION_ID);
		RESERVATION.setLocator(LOCATOR);
		RESERVATION.setPerson(PERSON);
		RESERVATION.setDate(DATE);
		RESERVATION.setTurn(TURN_NAME_);
		
	}
	
	@Test
	public void deleteReservationTest() throws BookingException{
		
		Mockito.when(reservationRepository.findByLocator(LOCATOR)).thenReturn(OPTIONAL_RESERVATION);
		Mockito.when(reservationRepository.deleteByLocator(LOCATOR)).thenReturn(OPTIONAL_RESERVATION);
		
		final String response = cancelReservationServiceImpl.deleteReservation(LOCATOR);
		
		assertEquals(response, RESERVATION_DELETED);
		
		
	}
	
	@Test
	public void deleteReservationFindByLocatorTestError() throws BookingException{
		
		assertThrows(BookingException.class, () -> {
			Mockito.when(reservationRepository.findByLocator(LOCATOR)).thenReturn(OPTIONAL_RESERVATION_EMPTY);
			Mockito.when(reservationRepository.deleteByLocator(LOCATOR)).thenReturn(OPTIONAL_RESERVATION);
			
			cancelReservationServiceImpl.deleteReservation(LOCATOR);
			fail();
		});
		
		
		
		
	}
	
	@Test
	public void deleteReservationInternalServerError() throws BookingException{
		
		assertThrows(BookingException.class, () -> {
			Mockito.when(reservationRepository.findByLocator(LOCATOR)).thenReturn(OPTIONAL_RESERVATION);
			//Mockito.when(reservationRepository.deleteByLocator(LOCATOR)).thenReturn(OPTIONAL_RESERVATION_EMPTY);
			
			Mockito.doThrow(new RuntimeException()).when(reservationRepository).deleteByLocator(LOCATOR);
			
			cancelReservationServiceImpl.deleteReservation(LOCATOR);
			fail();
		});
		
		
		
		
	}

}
