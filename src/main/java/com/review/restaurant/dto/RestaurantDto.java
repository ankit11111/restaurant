package com.review.restaurant.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class RestaurantDto {

	private Long restaurantId;

	@NotBlank(message = "Restaurant name cannot be blank")
    @Size(min = 2, max = 50, message = "Restaurant name must be between 2 and 50 characters")
	private String restaurantName;

	@NotNull(message = "Cuisine type cannot be blank")
	private String cuisineType;

	@NotBlank(message = "Address cannot be blank")
    @Size(max = 200, message = "Address should not exceed 200 characters")
	private String address;

	@NotNull(message = "Price range must be provided")
	private PriceRange priceRange;

	public RestaurantDto() {
		super();
	}
	public Long getRestaurantId() {
		return restaurantId;
	}
	public void setRestaurantId(Long restaurantId) {
		this.restaurantId = restaurantId;
	}
	public String getRestaurantName() {
		return restaurantName;
	}
	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}
	public String getCuisineType() {
		return cuisineType;
	}
	public void setCuisineType(String cuisineType) {
		this.cuisineType = cuisineType;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public PriceRange getPriceRange() {
		return priceRange;
	}
	public void setPriceRange(PriceRange priceRange) {
		this.priceRange = priceRange;
	}

	@Override
	public String toString() {
		return "RestaurantDto [restaurantId=" + restaurantId + ", restaurantName=" + restaurantName + ", cuisineType="
				+ cuisineType + ", address=" + address + ", priceRange=" + priceRange + "]";
	}
}
