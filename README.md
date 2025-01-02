# Restaurant Review Service

This is a Spring Boot application that allows users to manage restaurant reviews. It provides APIs to create restaurants, retrieve restaurant details, create reviews, and fetch reviews for specific restaurants. Additionally, it calculates the average rating of a restaurant based on user reviews.

## Features
### Restaurant Management
* Create new restaurant records.
* Retrieve restaurant details by ID.

### Restaurant Reviews
* Create reviews for specific restaurants.
* View all reviews for a restaurant with pagination support.
* Calculate the average review score for a restaurant.

## Technologies Used
* Java 17 (or higher)
* Spring Boot 3.x
* Spring Data JPA for database operations
* H2 Database for in-memory storage (or replace with any other RDBMS like MySQL/PostgreSQL)
* Spring Security for basic authentication
* SLF4J with Logback for logging
* Swagger/OpenAPI for API documentation

## Setup
### 1. Clone the Repository
Clone this repository to your local machine:

`git clone https://github.com/yourusername/restaurant.git`
`cd restaurant`

### 2. Build the project
To build the project, you need to use Gradle.
`./gradlew build`

### 3. Run the application
Run RestaurantApplication.java

## API Documentation (Swagger UI)
* Swagger UI URL: http://localhost:8080/swagger-ui/index.html

