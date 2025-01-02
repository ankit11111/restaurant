package com.review.restaurant.converter;

import com.review.restaurant.dto.PriceRange;
import com.review.restaurant.dto.RestaurantDto;
import com.review.restaurant.entity.Restaurant;

public class RestaurantConverter {

	private RestaurantConverter() {
		// Singleton
	}

	public static Restaurant toRestaurant(final RestaurantDto restaurantDto) {
		Restaurant restaurant = new Restaurant();
		restaurant.setAddress(restaurantDto.getAddress());
		restaurant.setCuisineType(restaurantDto.getCuisineType());
		restaurant.setPriceRange(restaurantDto.getPriceRange().name());
		restaurant.setRestaurantName(restaurantDto.getRestaurantName());
		return restaurant;
	}

	public static RestaurantDto toRestaurantDto(final Restaurant restaurant) {
		RestaurantDto restaurantDto = new RestaurantDto();
		restaurantDto.setAddress(restaurant.getAddress());
		restaurantDto.setCuisineType(restaurant.getCuisineType());
		restaurantDto.setPriceRange(PriceRange.valueOf(restaurant.getPriceRange()));
		restaurantDto.setRestaurantName(restaurant.getRestaurantName());
		restaurantDto.setRestaurantId(restaurant.getRestaurantId());
		return restaurantDto;
	}
}
