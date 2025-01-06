package com.review.restaurant.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.review.restaurant.converter.RestaurantConverter;
import com.review.restaurant.dto.RestaurantDto;
import com.review.restaurant.entity.Restaurant;
import com.review.restaurant.entity.RestaurantReview;
import com.review.restaurant.repository.RestaurantRepository;
import com.review.restaurant.repository.RestaurantReviewRepository;
import com.review.restaurant.service.RestaurantService;

@Service
public class RestaurantServiceImpl implements RestaurantService {

	@Autowired
	private RestaurantRepository restaurantRepository;

	@Autowired
	private RestaurantReviewRepository restauranReviewRepository;

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

	@Override
	public List<RestaurantDto> listAllRestaurants(Double minimumRating) {
		List<Restaurant> restaurantList = null;
		List<RestaurantDto> restaurantDtoList = new ArrayList<RestaurantDto>();
		restaurantList = restaurantRepository.findAll();
		if (Objects.nonNull(minimumRating)) {
			restaurantList.stream().forEach(restaurant -> {
				Double sum = 0.0;
				for (RestaurantReview review : restaurant.getReviews()) {
					sum += review.getRating();
				}
				Double averageRating = sum / restaurant.getReviews().size();
				if (averageRating >= minimumRating) {
					restaurantDtoList.add(RestaurantConverter.toRestaurantDto(restaurant));
				}

			});
		} else {
			restaurantList.stream().forEach(restaurant -> {
				restaurantDtoList.add(RestaurantConverter.toRestaurantDto(restaurant));

			});
		}
		return restaurantDtoList;
	}

}
