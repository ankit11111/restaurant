package com.review.restaurant.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.review.restaurant.constants.PathConstants;
import com.review.restaurant.dto.RestaurantDto;
import com.review.restaurant.service.RestaurantService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
public class RestaurantApi {

    private static final Logger logger = LoggerFactory.getLogger(RestaurantApi.class);

	@Autowired
	private RestaurantService restaurantService;

	@Operation(summary = "Create a new restaurant", 
	           description = "Creates a new restaurant record with the provided details.")
	@ApiResponses(value = { 
	    @ApiResponse(responseCode = "201", description = "Restaurant created successfully", 
	                 content = { @Content(mediaType = "application/json", 
	                                       schema = @Schema(implementation = String.class)) }),
	    @ApiResponse(responseCode = "400", description = "Invalid input provided", 
	                 content = @Content), 
	    @ApiResponse(responseCode = "401", description = "Unauthorized", 
	                 content = @Content) })
	@RequestMapping(method = RequestMethod.POST,
			value = PathConstants.CREATE_RESTAURANT_PATH,
			produces = {"application/json"},
			consumes = {"application/json"}
	)
	public ResponseEntity<String> createRestaurant(@Valid @RequestBody RestaurantDto restaurant) {
		logger.info("Creating Restaurant with details : {}", restaurant);
		Long restaurantId = restaurantService.createRestaurant(restaurant);
		return ResponseEntity.status(HttpStatus.CREATED).body("{\"restaurantId\":" + restaurantId + "}");
	}

	@Operation(summary = "Retrieve a created restaurant", 
	           description = "Retrieve a restaurant record using restaurant ID.")
	@ApiResponses(value = { 
	    @ApiResponse(responseCode = "200", 
	                 content = { @Content(mediaType = "application/json", 
	                                       schema = @Schema(implementation = RestaurantDto.class)) }),
	    @ApiResponse(responseCode = "404", description = "Restaurant does not exist", 
	                 content = @Content), 
	    @ApiResponse(responseCode = "401", description = "Unauthorized", 
	                 content = @Content) })
	@RequestMapping(method = RequestMethod.GET,
			value = PathConstants.GET_RESTAURANT_PATH,
			produces = {"application/json"})
	public ResponseEntity<RestaurantDto> getRestaurant(@PathVariable("restaurantId") final Long restaurantId) {
		logger.info("Fetching restaurant infor for restaurant id {}", restaurantId);
		RestaurantDto restaurantDto = restaurantService.getRestaurantById(restaurantId);
		if (restaurantDto == null) {
            // If no restaurant is found, return a 404 Not Found response
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Return the restaurant wrapped in a 200 OK response
        return ResponseEntity.status(HttpStatus.OK).body(restaurantDto);
	}
}
