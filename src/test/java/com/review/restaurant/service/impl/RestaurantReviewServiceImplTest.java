package com.review.restaurant.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.review.restaurant.dto.AverageReview;
import com.review.restaurant.dto.RestaurantReviewDto;
import com.review.restaurant.entity.Restaurant;
import com.review.restaurant.entity.RestaurantReview;
import com.review.restaurant.exception.handler.RestaurantNotFoundException;
import com.review.restaurant.repository.RestaurantRepository;
import com.review.restaurant.repository.RestaurantReviewRepository;

public class RestaurantReviewServiceImplTest {

    @Mock
    private RestaurantReviewRepository restaurantReviewRepository;

    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private RestaurantReviewServiceImpl restaurantReviewService;

    private Restaurant restaurant;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        restaurant = new Restaurant();
        restaurant.setRestaurantId(1L);
        restaurant.setRestaurantName("Test Restaurant");
        restaurant.setCuisineType("Indian");
        restaurant.setAddress("123 Main Street");
        restaurant.setPriceRange("Medium");
    }

    @Test
    void testCreateRestaurantReview_Success() {
        RestaurantReviewDto restaurantReviewDto = new RestaurantReviewDto();
        restaurantReviewDto.setRating(5);
        restaurantReviewDto.setComment("Great food!");
        restaurantReviewDto.setVisitDate(LocalDate.now());

        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));

        restaurantReviewService.createRestaurantReview(restaurantReviewDto, 1L);

        verify(restaurantReviewRepository, times(1)).save(any(RestaurantReview.class));
    }

    @Test
    void testCreateRestaurantReview_RestaurantNotFound() {
        RestaurantReviewDto restaurantReviewDto = new RestaurantReviewDto();
        restaurantReviewDto.setRating(5);
        restaurantReviewDto.setComment("Great food!");
        restaurantReviewDto.setVisitDate(LocalDate.now());

        when(restaurantRepository.findById(1L)).thenReturn(Optional.empty());

        RestaurantNotFoundException exception = assertThrows(RestaurantNotFoundException.class, () -> {
            restaurantReviewService.createRestaurantReview(restaurantReviewDto, 1L);
        });

        assertEquals("{\"error\":\"Restaurant Not found with provided id\"}", exception.getMessage());
    }

    @Test
    void testFindAverageReview_Success() {
    	LocalDate date1 = LocalDate.now();
        List<RestaurantReview> reviews = Arrays.asList(
                new RestaurantReview(1L, 4, "Good food", date1, "Active"),
                new RestaurantReview(1L, 5, "Excellent food", date1, "Active")
        );

        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));
        when(restaurantReviewRepository.findAllByRestaurantId(1L)).thenReturn(reviews);

        AverageReview averageReview = restaurantReviewService.findAverageReview(1L);

        assertEquals(1L, averageReview.getRestaurantId());
        assertEquals(4.5, averageReview.getAverageReview());
    }

    @Test
    void testFindAverageReview_RestaurantNotFound() {
        when(restaurantRepository.findById(1L)).thenReturn(Optional.empty());

        RestaurantNotFoundException exception = assertThrows(RestaurantNotFoundException.class, () -> {
            restaurantReviewService.findAverageReview(1L);
        });

        assertEquals("{\"error\":\"Restaurant Not found with provided id\"}", exception.getMessage());
    }

    @Test
    void testFindAverageReview_NoReviews() {
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));
        when(restaurantReviewRepository.findAllByRestaurantId(1L)).thenReturn(Collections.emptyList());

        AverageReview averageReview = restaurantReviewService.findAverageReview(1L);

        assertEquals(1L, averageReview.getRestaurantId());
        assertEquals(0.0, averageReview.getAverageReview());
    }

    @Test
    void testGetAllReviewsForRestaurant_Success() {
    	LocalDate date1 = LocalDate.now();
        List<RestaurantReview> reviews = Arrays.asList(
                new RestaurantReview(1L, 4, "Good food", date1, "PENDING"),
                new RestaurantReview(1L, 5, "Excellent food", date1, "PENDING")
        );
        Pageable pageable = PageRequest.of(0, 10);
        Page<RestaurantReview> reviewPage = mock(Page.class);
        when(reviewPage.getContent()).thenReturn(reviews);

        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));
        when(restaurantReviewRepository.findAllByRestaurantId(1L, pageable)).thenReturn(reviewPage);

        List<RestaurantReviewDto> reviewDtos = restaurantReviewService.getAllReviewsForRestaurant(1L, 0, 10);

        assertEquals(2, reviewDtos.size());
        assertEquals(4, reviewDtos.get(0).getRating());
        assertEquals("Good food", reviewDtos.get(0).getComment());
    }

    @Test
    void testGetAllReviewsForRestaurant_RestaurantNotFound() {
        when(restaurantRepository.findById(1L)).thenReturn(Optional.empty());

        RestaurantNotFoundException exception = assertThrows(RestaurantNotFoundException.class, () -> {
            restaurantReviewService.getAllReviewsForRestaurant(1L, 0, 10);
        });

        assertEquals("{\"error\":\"Restaurant Not found with provided id\"}", exception.getMessage());
    }
}

