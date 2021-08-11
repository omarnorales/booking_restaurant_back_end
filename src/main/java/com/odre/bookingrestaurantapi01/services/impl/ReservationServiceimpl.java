package com.odre.bookingrestaurantapi01.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.annotations.VisibleForTesting;
import com.odre.bookingrestaurantapi01.entities.Reservation;
import com.odre.bookingrestaurantapi01.entities.Restaurant;
import com.odre.bookingrestaurantapi01.entities.Turn;
import com.odre.bookingrestaurantapi01.exceptions.BookingException;
import com.odre.bookingrestaurantapi01.exceptions.InternalServerErrorException;
import com.odre.bookingrestaurantapi01.exceptions.NotFoundException;
import com.odre.bookingrestaurantapi01.json.CreateReservationRest;
import com.odre.bookingrestaurantapi01.json.ReservationRest;
import com.odre.bookingrestaurantapi01.repositories.ReservationRepository;
import com.odre.bookingrestaurantapi01.repositories.RestaurantRepository;
import com.odre.bookingrestaurantapi01.repositories.TurnRepository;
import com.odre.bookingrestaurantapi01.services.EmailService;
import com.odre.bookingrestaurantapi01.services.ReservationService;

@Service
public class ReservationServiceimpl implements ReservationService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ReservationServiceimpl.class);

	@Autowired
	ReservationRepository reservationRepository;

	@Autowired
	RestaurantRepository restaurantRepository;
	
	@Autowired
	private TurnRepository turnRepositoty;
	
	@Autowired
	private EmailService  emailService;

	public static final ModelMapper modelMapper = new ModelMapper();

	public ReservationRest getReservationById(Long reservationId) throws BookingException {
		return modelMapper.map(getReservationEntity(reservationId), ReservationRest.class);
	}


	public String createReservation(final CreateReservationRest createReservationRest) throws BookingException {

		// verifica que exista en restaurant
		final Restaurant restaurant = restaurantRepository.findById(createReservationRest.getRestaurantId())
				.orElseThrow(()-> new NotFoundException("RESTAURANT_NOT_FOUND","RESTAURANT_NOT_FOUND"));
		
		// verifica que exista el turno
		final Turn turn = turnRepositoty.findById(createReservationRest.getTurnId())
				.orElseThrow(()-> new NotFoundException("TURN_NOT_FOUND","TURN_NOT_FOUND"));
		
		if (reservationRepository.findByTurnAndRestaurantId(turn.getName(), restaurant.getId()).isPresent()) {
			
			throw new NotFoundException("RESERVATION_ALREADY_EXIST", "RESERVATION_ALREADY_EXIST");
			
		}
		
//		System.out.println(createReservationRest.getDate());
//		
//		if (reservationRepository.findByTurnAndRestaurantIdAndDate(turn.getName(), restaurant.getId(), createReservationRest.getDate()).isPresent()) {
//			
//			throw new NotFoundException("RESERVATION_ALREADY_EXIST", "RESERVATION_ALREADY_EXIST");
//			
//		}
		
//		if (reservationRepository.queryFindByTurnAndRestaurantIdAndDate(turn.getName(), 
//																		restaurant.getId(), 
//																		createReservationRest.getDate()
//																		).size() > 0 ) {
//			
//			throw new NotFoundException("RESERVATION_ALREADY_EXIST", "RESERVATION_ALREADY_EXIST");
//			
//		}

		String locator = generateLocator(restaurant, createReservationRest);

		final Reservation reservation = new Reservation();
		reservation.setLocator(locator);
		reservation.setPerson(createReservationRest.getPerson());
		reservation.setDate(createReservationRest.getDate());
		reservation.setRestaurant(restaurant);
		reservation.setTurn(turn.getName());
		reservation.setName(createReservationRest.getName());
		reservation.setEmail(createReservationRest.getEmail());
		try {
			
			reservationRepository.save(reservation);
			
		} catch (final Exception e) {
			LOGGER.error("INTERNAL_SERVER_ERROR", e);
			throw new InternalServerErrorException("INTERNAL_SERVER_ERROR", "INTERNAL_SERVER_ERROR");
		}
		
		this.emailService.processSendEmail(reservation.getEmail(), "RESERVATION" , reservation.getName());
		

		return locator;
	}

	private String generateLocator(Restaurant restaurant, CreateReservationRest createReservationRest)
			throws BookingException {

		return restaurant.getName() + createReservationRest.getTurnId();

	}
	
	@VisibleForTesting
	private Reservation getReservationEntity(Long reservationId) throws BookingException {
		return reservationRepository.findById(reservationId)
				.orElseThrow(() -> new NotFoundException("SNOT-404-1", "RESERVATION_NOT_FOUND"));
	}

	public List<ReservationRest> Reservations() throws BookingException {
		final List<Reservation> ReservationsEntity = reservationRepository.findAll();

		
		return ReservationsEntity.stream().map(service -> modelMapper.map(service, ReservationRest.class))
				.collect(Collectors.toList());
	}


	
	public List<ReservationRest> getReservationByRestaurantId(Long restaurantId) throws BookingException {
		
		final List<Reservation> ReservationsEntity = reservationRepository.queryFindByRestaurantId(restaurantId);

		return ReservationsEntity.stream().map(service -> modelMapper.map(service, ReservationRest.class))
				.collect(Collectors.toList());
	}


	@Override
	public void updateReservation(Boolean payment, String locator) throws BookingException {
		final Reservation reservation = reservationRepository.findByLocator(locator)
				.orElseThrow(() -> new NotFoundException("CODE_LOCATOR_NOT_FOUND", "CODE_LOCATOR_NOT_FOUND"));
		
		reservation.setPayment(true);
		
		try {
			reservationRepository.save(reservation);
		} catch (Exception e) {
			LOGGER.error("INTERNAL_SERVER_ERROR", e);
			throw new InternalServerErrorException("INTERNAL_SERVER_ERROR", "INTERNAL_SERVER_ERROR");
		}
		
	}
	
	



}
