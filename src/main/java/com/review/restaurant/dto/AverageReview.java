package com.review.restaurant.dto;

public class AverageReview {

	private Long restaurantId;
	private Double averageReview;

	public AverageReview() {
		super();
	}

	public Long getRestaurantId() {
		return restaurantId;
	}
	public void setRestaurantId(Long restaurantId) {
		this.restaurantId = restaurantId;
	}
	public Double getAverageReview() {
		return averageReview;
	}
	public void setAverageReview(Double averageReview) {
		this.averageReview = averageReview;
	}
}
