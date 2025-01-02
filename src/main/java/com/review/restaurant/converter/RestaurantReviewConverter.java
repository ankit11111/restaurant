package com.review.restaurant.converter;

import com.review.restaurant.dto.RestaurantReviewDto;
import com.review.restaurant.dto.Status;
import com.review.restaurant.entity.RestaurantReview;

public class RestaurantReviewConverter {

	private RestaurantReviewConverter() {
		// Singleton
	}

	public static RestaurantReview toRestaurantReview(final RestaurantReviewDto restaurantReviewDto) {
		RestaurantReview restaurantReview = new RestaurantReview();
		restaurantReview.setRating(restaurantReviewDto.getRating());
		restaurantReview.setComment(restaurantReviewDto.getComment());
		restaurantReview.setStatus(Status.PENDING.name());
		restaurantReview.setVisitDate(restaurantReviewDto.getVisitDate());
		return restaurantReview;
	}

	public static RestaurantReviewDto toRestaurantReviewDto(final RestaurantReview restaurantReview) {
		RestaurantReviewDto restaurantReviewDto = new RestaurantReviewDto();
		restaurantReviewDto.setRating(restaurantReview.getRating());
		restaurantReviewDto.setComment(restaurantReview.getComment());
		restaurantReviewDto.setStatus(Status.valueOf(restaurantReview.getStatus()));
		restaurantReviewDto.setVisitDate(restaurantReview.getVisitDate());
		return restaurantReviewDto;
	}
}
