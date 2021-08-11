package com.odre.bookingrestaurantapi01.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.odre.bookingrestaurantapi01.entities.Restaurant;


@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long>{
	
	Optional<Restaurant> findById(Long RestaurantId);
	
	Optional<Restaurant> findByName(String nameRestaurant);
	
	@Query("select REST FROM Restaurant REST")
	public List<Restaurant> findRestaurants();
	

}
