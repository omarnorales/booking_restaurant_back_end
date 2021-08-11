package com.odre.bookingrestaurantapi01.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.odre.bookingrestaurantapi01.entities.Notification;


@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
	
	//Notification findById(Long id);
	
	Optional<Notification> findByTemplateCode(String templatecode);
	
	

}
