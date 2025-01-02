package com.review.restaurant.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.review.restaurant.dto.PriceRange;
import com.review.restaurant.dto.RestaurantDto;
import com.review.restaurant.entity.Restaurant;
import com.review.restaurant.repository.RestaurantRepository;

public class RestaurantServiceImplTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private RestaurantServiceImpl restaurantService;

    private RestaurantDto restaurantDto;
    private Restaurant restaurant;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Mock RestaurantDto and Restaurant entity for tests
        restaurantDto = new RestaurantDto();
        restaurantDto.setRestaurantName("Test Restaurant");
        restaurantDto.setCuisineType("Italian");
        restaurantDto.setAddress("123 Main Street");
        restaurantDto.setPriceRange(PriceRange.MEDIUM);

        restaurant = new Restaurant();
        restaurant.setRestaurantId(1L);
        restaurant.setRestaurantName("Test Restaurant");
        restaurant.setCuisineType("Italian");
        restaurant.setAddress("123 Main Street");
        restaurant.setPriceRange("MEDIUM");
    }

    @Test
    void testCreateRestaurant_Success() {
        // Arrange
        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(restaurant);

        // Act
        Long restaurantId = restaurantService.createRestaurant(restaurantDto);

        // Assert
        assertEquals(1L, restaurantId);
        verify(restaurantRepository, times(1)).save(any(Restaurant.class));
    }

    @Test
    void testGetRestaurantById_Success() {
        // Arrange
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));

        // Act
        RestaurantDto result = restaurantService.getRestaurantById(1L);

        // Assert
        assertNotNull(result);
        assertEquals("Test Restaurant", result.getRestaurantName());
        assertEquals("Italian", result.getCuisineType());
        assertEquals("123 Main Street", result.getAddress());
        assertEquals("MEDIUM", result.getPriceRange().name());
    }

    @Test
    void testGetRestaurantById_NotFound() {
        // Arrange
        when(restaurantRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        RestaurantDto result = restaurantService.getRestaurantById(1L);

        // Assert
        assertNull(result);
    }
}

