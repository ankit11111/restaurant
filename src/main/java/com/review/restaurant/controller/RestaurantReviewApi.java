package com.review.restaurant.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.review.restaurant.constants.PathConstants;
import com.review.restaurant.dto.AverageReview;
import com.review.restaurant.dto.RestaurantReviewDto;
import com.review.restaurant.service.RestaurantReviewService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
public class RestaurantReviewApi {

	private static final Logger logger = LoggerFactory.getLogger(RestaurantReviewApi.class);

	@Autowired
	private RestaurantReviewService restaurantReviewService;

	@Operation(
	        summary = "Create a new review for a restaurant",
	        description = "Submit a new review for the specified restaurant using its ID. The review will be associated with the restaurant."
	)
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "201", 
	                 description = "Successfully created the restaurant review", 
	                 content = @Content(mediaType = "application/json", 
	                                    schema = @Schema(implementation = String.class))),
	    @ApiResponse(responseCode = "400", 
	                 description = "Invalid input, review data or restaurant ID is missing or malformed", 
	                 content = @Content),
	    @ApiResponse(responseCode = "404", 
	                 description = "Restaurant not found with the provided ID", 
                     content = @Content)
	})
	@RequestMapping(method = RequestMethod.POST,
			value = PathConstants.CREATE_RESTAURANT_REVIEW,
			produces = {"application/json"},
			consumes = {"application/json"}
	)
	public void createRestaurantReview(@PathVariable("restaurantId") final Long restaurantId,
			@Valid @RequestBody RestaurantReviewDto restaurantReview) {
		logger.info("Providing review {} for Restaurant id {}", restaurantReview, restaurantId);
		restaurantReviewService.createRestaurantReview(restaurantReview, restaurantId);
	}

	@Operation(
	        summary = "Get all reviews for a specific restaurant",
	        description = "Retrieve a paginated list of all reviews associated with a specific restaurant."
	)
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "200", 
	                 description = "Successfully retrieved reviews",
	                 content = @Content(mediaType = "application/json", 
	                                    schema = @Schema(implementation = RestaurantReviewDto.class))),
	    @ApiResponse(responseCode = "400", 
	                 description = "Invalid pagination parameters (page or size)", 
	                 content = @Content),
	    @ApiResponse(responseCode = "404", 
	                 description = "Restaurant not found with the provided ID", 
	                 content = @Content)
	})
	@RequestMapping(method = RequestMethod.GET,
			value = PathConstants.GET_RESTAURANT_REVIEWS,
			produces = {"application/json" })
	public ResponseEntity<List<RestaurantReviewDto>> getAllReviewsForRestaurant(
			@PathVariable("restaurantId") final Long restaurantId,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "10") int size) {
		logger.info("Getting all th reviews for restaurant id {}, page [] and size {}", restaurantId, page, size);
		List<RestaurantReviewDto> reviews = restaurantReviewService.getAllReviewsForRestaurant(restaurantId, page,
				size);
		return ResponseEntity.ok(reviews);
	}

	@Operation(
	        summary = "Get the average review for a specific restaurant",
	        description = "Retrieve the average review rating for a specific restaurant"
	        		+ " based on all the reviews submitted for it."
	)
	@ApiResponses(value = {
		    @ApiResponse(responseCode = "200", 
		                 description = "Successfully retrieved the average review",
		                 content = @Content(mediaType = "application/json", 
		                                    schema = @Schema(implementation = AverageReview.class))),
		    @ApiResponse(responseCode = "404", 
		                 description = "Restaurant not found with the provided ID", 
		                 content = @Content)
		})
	@RequestMapping(method = RequestMethod.GET,
			value = PathConstants.GET_AVERAGE_RESTAURANT_REVIEW,
			produces = {"application/json"})
	public ResponseEntity<AverageReview> getAverageRestaurantReview(
			@PathVariable("restaurantId") final Long restaurantId) {
		logger.info("Getting average info for restaurant id {}", restaurantId);
		return ResponseEntity.status(HttpStatus.OK).body(restaurantReviewService.findAverageReview(restaurantId));
	}
}
