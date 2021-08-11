package com.odre.bookingrestaurantapi01.services;

import com.odre.bookingrestaurantapi01.exceptions.BookingException;
import com.odre.bookingrestaurantapi01.json.NotificationRest;

public interface NotificationService {
	
	public NotificationRest getNotificationById(Long notificationId)  throws BookingException;

}
