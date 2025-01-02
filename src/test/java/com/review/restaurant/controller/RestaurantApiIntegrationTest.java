package com.review.restaurant.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.review.restaurant.dto.PriceRange;
import com.review.restaurant.dto.RestaurantDto;
import com.review.restaurant.service.RestaurantService;

@SpringBootTest
@AutoConfigureMockMvc
public class RestaurantApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private RestaurantService restaurantService;

    @InjectMocks
    private RestaurantApi restaurantApi;

    private RestaurantDto restaurantDto;

    private static final String AUTH_HEADER = "Basic YWRtaW46YWRtaW4xMjM=";

    @BeforeEach
    void setUp() {
        // Set up mock restaurant data
        restaurantDto = new RestaurantDto();
        restaurantDto.setRestaurantName("Test Restaurant");
        restaurantDto.setCuisineType("Italian");
        restaurantDto.setAddress("123 Main Street");
        restaurantDto.setPriceRange(PriceRange.MEDIUM);
    }

    @Test
    void testCreateRestaurant() throws Exception {
        // Arrange: Mock the service call to return a restaurant ID
        when(restaurantService.createRestaurant(any(RestaurantDto.class))).thenReturn(1L);

        // Act and Assert: Perform a POST request to create a restaurant
        mockMvc.perform(post("/restaurant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(restaurantDto))
                .header("Authorization", AUTH_HEADER))
                .andExpect(status().isCreated());
    }

    @Test
    void testGetRestaurant_Success() throws Exception {
        // Arrange: Mock the service call to return a restaurant
        when(restaurantService.getRestaurantById(1L)).thenReturn(restaurantDto);
        mockMvc.perform(post("/restaurant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(restaurantDto))
                .header("Authorization", AUTH_HEADER))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.restaurantId").value(1L));

        // Act and Assert: Perform a GET request to fetch the restaurant by ID
        mockMvc.perform(get("/restaurant/{restaurantId}", 1L)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", AUTH_HEADER))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.restaurantName").value("Test Restaurant"))
                .andExpect(jsonPath("$.cuisineType").value("Italian"))
                .andExpect(jsonPath("$.address").value("123 Main Street"))
                .andExpect(jsonPath("$.priceRange").value("MEDIUM"));
    }

    @Test
    void testGetRestaurant_NotFound() throws Exception {
        // Arrange: Mock the service call to return null (restaurant not found)
        when(restaurantService.getRestaurantById(3L)).thenReturn(null);

        // Act and Assert: Perform a GET request to fetch a non-existing restaurant
        mockMvc.perform(get("/restaurant/{restaurantId}", 3L)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", AUTH_HEADER))
                .andExpect(status().isNotFound());
    }
}
