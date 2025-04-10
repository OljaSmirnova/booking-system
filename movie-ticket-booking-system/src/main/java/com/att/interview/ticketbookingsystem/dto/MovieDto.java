package com.att.interview.ticketbookingsystem.dto;

import jakarta.validation.constraints.*;

import static com.att.interview.ticketbookingsystem.api.ValidationConstants.*;

public record MovieDto(
		@NotEmpty(message = MISSING_MOVIE_TITLE)
		String title,
		
		@NotEmpty(message = MISSING_MOVIE_GENRE)
		String genre,
		
		@Positive(message = WRONG_DURATION)
		Integer duration,
		
		@DecimalMin(value = MIN_MOVIE_RATING, message = WRONG_MIN_MOVIE_RATING_VALUE + MIN_MOVIE_RATING)
		@DecimalMax(value = MAX_MOVIE_RATING, message = WRONG_MAX_MOVIE_RATING_VALUE + MAX_MOVIE_RATING)
		double rating,
		
		@NotNull(message = MISSING_MOVIE_RELEASE_YEAR)
		@Min(value = MIN_MOVIE_RELEASE_YEAR, message = WRONG_MIN_MOVIE_RELEASE_YEAR_VALUE)
		Integer releaseYear
		) {
	
}
