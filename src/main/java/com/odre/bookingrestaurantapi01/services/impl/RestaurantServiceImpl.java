package com.odre.bookingrestaurantapi01.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.odre.bookingrestaurantapi01.entities.Restaurant;
import com.odre.bookingrestaurantapi01.exceptions.BookingException;
import com.odre.bookingrestaurantapi01.exceptions.NotFoundException;
import com.odre.bookingrestaurantapi01.json.RestaurantRest;
import com.odre.bookingrestaurantapi01.repositories.RestaurantRepository;
import com.odre.bookingrestaurantapi01.services.RestaurantService;

@Service
public class RestaurantServiceImpl implements RestaurantService {

	@Autowired
	RestaurantRepository restaurantRepository;

	public static final ModelMapper modelMapper = new ModelMapper();

	public RestaurantRest getRestaurantById(Long restaurantId) throws BookingException {

		// llamado a metodo getRestaurantEntity, con tipo destino 'RestaurantRest'
		return modelMapper.map(getRestaurantEntity(restaurantId), RestaurantRest.class);
	}

	public List<RestaurantRest> getRestaurants() throws BookingException {
		final List<Restaurant> RestaurantsEntity = restaurantRepository.findAll();

		// stream() recorre y genera una collection
		// map(service, RestaurantRest.class) mapea el service en la clase RestaurantRest
		return RestaurantsEntity.stream().map(service -> modelMapper.map(service, RestaurantRest.class))
				//hace un collect que toma esos objetos mapeados y los convierte en una lista
				.collect(Collectors.toList());
	}

	// retorna instancia de entidad restaurant
	private Restaurant getRestaurantEntity(Long restaurantId) throws BookingException {
		return restaurantRepository.findById(restaurantId)
				.orElseThrow(() -> new NotFoundException("SNOT-404-1", "RESTAURANT_NOT_FOUND"));
	}

}
