package com.review.restaurant.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.review.restaurant.entity.RestaurantReview;

public interface RestaurantReviewRepository extends JpaRepository<RestaurantReview, Long> { 

	@Query("SELECT r FROM RestaurantReview r WHERE r.restaurant.restaurantId = :restaurantId")
    List<RestaurantReview> findAllByRestaurantId(@Param("restaurantId") Long restaurantId);

	@Query("SELECT r FROM RestaurantReview r WHERE r.restaurant.restaurantId = :restaurantId")
	Page<RestaurantReview> findAllByRestaurantId(@Param("restaurantId") Long restaurantId, Pageable pageable);
}
