package com.review.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.review.restaurant.entity.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> { 

}
