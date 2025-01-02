package com.review.restaurant.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.review.restaurant.converter.RestaurantConverter;
import com.review.restaurant.dto.RestaurantDto;
import com.review.restaurant.entity.Restaurant;
import com.review.restaurant.repository.RestaurantRepository;
import com.review.restaurant.service.RestaurantService;

@Service
public class RestaurantServiceImpl implements RestaurantService {

	@Autowired
	private RestaurantRepository restaurantRepository;

	@Override
	public Long createRestaurant(RestaurantDto restaurantDto) {
		return restaurantRepository.save(RestaurantConverter.toRestaurant(restaurantDto)).getRestaurantId();
	}

	@Override
	public RestaurantDto getRestaurantById(Long restaurantId) {
		Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantId);
		if(restaurant.isPresent()) {
			return RestaurantConverter.toRestaurantDto(restaurant.get());
		}
		return null;
	}

}
