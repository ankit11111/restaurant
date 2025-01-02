package com.review.restaurant.service;

import com.review.restaurant.dto.RestaurantDto;

public interface RestaurantService {

	Long createRestaurant(final RestaurantDto restaurantDto);

	RestaurantDto getRestaurantById(final Long restaurantId);
}
