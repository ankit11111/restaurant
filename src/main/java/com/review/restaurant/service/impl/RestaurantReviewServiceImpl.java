package com.review.restaurant.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.review.restaurant.converter.RestaurantReviewConverter;
import com.review.restaurant.dto.AverageReview;
import com.review.restaurant.dto.RestaurantReviewDto;
import com.review.restaurant.entity.Restaurant;
import com.review.restaurant.entity.RestaurantReview;
import com.review.restaurant.exception.handler.RestaurantNotFoundException;
import com.review.restaurant.repository.RestaurantRepository;
import com.review.restaurant.repository.RestaurantReviewRepository;
import com.review.restaurant.service.RestaurantReviewService;

@Service
public class RestaurantReviewServiceImpl implements RestaurantReviewService {

	private static final Logger logger = LoggerFactory.getLogger(RestaurantReviewServiceImpl.class);

	@Autowired
	private RestaurantReviewRepository restaurantReviewRepository;

	@Autowired
	private RestaurantRepository restaurantRepository;

	@Override
	public void createRestaurantReview(RestaurantReviewDto restaurantReviewDto, final Long restuarantId) {
		Optional<Restaurant> restaurant = restaurantRepository.findById(restuarantId);
		if (restaurant.isPresent()) {
			logger.info("Restaurant found for restaurant id {}", restuarantId);
			RestaurantReview restaurantReview = RestaurantReviewConverter.toRestaurantReview(restaurantReviewDto);
			restaurantReview.setRestaurant(restaurant.get());
			restaurantReviewRepository.save(restaurantReview);
		} else {
			throw new RestaurantNotFoundException("{\"error\":\"Restaurant Not found with provided id\"}");
		}
	}

	@Override
	public AverageReview findAverageReview(Long restaurantId) {
		validateRestaurantId(restaurantId);
		logger.info("Restaurant found for restaurant id {}", restaurantId);
		List<RestaurantReview> restaurantReviews = restaurantReviewRepository.findAllByRestaurantId(restaurantId);
		AverageReview averageReview = new AverageReview();
		averageReview.setRestaurantId(restaurantId);

		if (CollectionUtils.isEmpty(restaurantReviews)) {
			logger.info("No reviews provided for restaurant id {}", restaurantId);
			averageReview.setAverageReview(0.0); // Default value if no reviews are provided.
		} else {
			double sum = 0;
			for (RestaurantReview review : restaurantReviews) {
				sum += review.getRating(); // Assuming rating is an integer value
			}

			averageReview.setAverageReview(sum / restaurantReviews.size());
		}
		return averageReview;
	}

	@Override
	public List<RestaurantReviewDto> getAllReviewsForRestaurant(Long restaurantId, int page, int size) {
		validateRestaurantId(restaurantId);
		logger.info("Restaurant found for restaurant id {}", restaurantId);

		// Create a Pageable object with the given page and size
		Pageable pageable = PageRequest.of(page, size);

		// Fetch paginated reviews from the repository
		Page<RestaurantReview> reviewsPage = restaurantReviewRepository.findAllByRestaurantId(restaurantId, pageable);

		// Convert entity to DTO
		List<RestaurantReviewDto> reviewDtos = reviewsPage.getContent().stream()
				.map(RestaurantReviewConverter::toRestaurantReviewDto).collect(Collectors.toList());

		return reviewDtos;
	}

	private void validateRestaurantId(Long restaurantId) {
		Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantId);
		if (!restaurant.isPresent()) {
			throw new RestaurantNotFoundException("{\"error\":\"Restaurant Not found with provided id\"}");
		}
	}

}
