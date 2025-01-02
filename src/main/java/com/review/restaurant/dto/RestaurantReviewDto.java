package com.review.restaurant.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class RestaurantReviewDto {

	@NotNull(message = "Rating cannot be blank")
    @Min(1)
	@Max(5)
	private Integer rating;

	@NotBlank(message = "Comment cannot be blank")
    @Size(min = 5, max = 100, message = "Comment must be between 5 and 100 characters")
	private String comment;
	@NotNull
	private LocalDate visitDate;
	private Status status;

	public RestaurantReviewDto() {
		super();
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public LocalDate getVisitDate() {
		return visitDate;
	}

	public void setVisitDate(LocalDate visitDate) {
		this.visitDate = visitDate;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "RestaurantReviewDto [rating=" + rating + ", comment=" + comment + ", visitDate=" + visitDate
				+ ", status=" + status + "]";
	}
}
