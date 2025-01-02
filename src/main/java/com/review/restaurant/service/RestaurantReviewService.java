package com.review.restaurant.service;

import java.util.List;

import com.review.restaurant.dto.AverageReview;
import com.review.restaurant.dto.RestaurantReviewDto;

public interface RestaurantReviewService {

	void createRestaurantReview(final RestaurantReviewDto restaurantReviewDto, final Long restaurantId);

	AverageReview findAverageReview(final Long restaurantId);

	List<RestaurantReviewDto> getAllReviewsForRestaurant(final Long restaurantId, int page, int size);
}
