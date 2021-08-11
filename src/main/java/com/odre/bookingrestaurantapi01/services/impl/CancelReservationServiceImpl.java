package com.odre.bookingrestaurantapi01.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.odre.bookingrestaurantapi01.entities.Reservation;
import com.odre.bookingrestaurantapi01.exceptions.BookingException;
import com.odre.bookingrestaurantapi01.exceptions.InternalServerErrorException;
import com.odre.bookingrestaurantapi01.exceptions.NotFoundException;
import com.odre.bookingrestaurantapi01.repositories.ReservationRepository;
import com.odre.bookingrestaurantapi01.services.CancelReservationService;
import com.odre.bookingrestaurantapi01.services.EmailService;

@Service
public class CancelReservationServiceImpl implements CancelReservationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CancelReservationServiceImpl.class);

	@Autowired
	ReservationRepository reservationRepository;
	
	@Autowired
	private EmailService  emailService;

	public String deleteReservation(String locator) throws BookingException {

		Reservation reservation = reservationRepository.findByLocator(locator)
				.orElseThrow(() -> new NotFoundException("LOCATOR_NOT_FOUND", "LOCATOR_NOT_FOUND"));

		try {
			reservationRepository.deleteByLocator(locator);
		} catch (Exception e) {
			LOGGER.error("INTERNAL_SERVER_ERROR", e);
			throw new InternalServerErrorException("INTERNAL_SERVER_ERROR", "INTERNAL_SERVER_ERROR");
		}
		
		this.emailService.processSendEmail(reservation.getEmail(), "CANCEL" , reservation.getName());

		return "LOCATOR_DELETED";
	}

}
