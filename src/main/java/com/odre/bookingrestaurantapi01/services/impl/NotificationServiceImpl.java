package com.odre.bookingrestaurantapi01.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.odre.bookingrestaurantapi01.entities.Notification;
import com.odre.bookingrestaurantapi01.exceptions.BookingException;
import com.odre.bookingrestaurantapi01.exceptions.NotFoundException;
import com.odre.bookingrestaurantapi01.json.NotificationRest;
import com.odre.bookingrestaurantapi01.repositories.NotificationRepository;
import com.odre.bookingrestaurantapi01.repositories.ReservationRepository;
import com.odre.bookingrestaurantapi01.services.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService {

	
	@Autowired
	NotificationRepository notificationRepository;
	
	@Autowired
	ReservationRepository reservationRepository;
	
	public static final ModelMapper modelMapper = new ModelMapper();
	
	
	public NotificationRest getNotificationById(Long notificationId) throws BookingException {
		
		return modelMapper.map(getNotificationEntity(notificationId), NotificationRest.class);
	}
	
	private Notification getNotificationEntity(Long notificationId) throws BookingException{
		return notificationRepository.findById(notificationId)
				.orElseThrow(() -> new NotFoundException("SNOT-404-1", "NOTIFICATION_NOT_FOUND"));
	}

}
