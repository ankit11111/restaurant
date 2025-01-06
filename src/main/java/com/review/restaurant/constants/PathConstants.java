package com.review.restaurant.constants;

public final class PathConstants {

	private PathConstants() {
		// cannot be initialized;
	}

	public static final String CREATE_RESTAURANT_PATH = "/restaurant";
	public static final String GET_ALL_RESTAURANTS = "/restaurants";
	public static final String GET_RESTAURANT_PATH = CREATE_RESTAURANT_PATH + "/{restaurantId}";
	public static final String CREATE_RESTAURANT_REVIEW = GET_RESTAURANT_PATH + "/review";
	public static final String GET_RESTAURANT_REVIEWS = GET_RESTAURANT_PATH + "/reviews";
	public static final String GET_AVERAGE_RESTAURANT_REVIEW = GET_RESTAURANT_PATH + "/review/average";
}
