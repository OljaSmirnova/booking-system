package com.att.interview.ticketbookingsystem.dto;

import static com.att.interview.ticketbookingsystem.api.ValidationConstants.*;

import jakarta.validation.constraints.*;

public record MovieTitleAndYearData(
		@NotEmpty(message = MISSING_MOVIE_TITLE)
		String title,
		
		@NotNull(message = MISSING_MOVIE_RELEASE_YEAR)
		@Min(value = MIN_MOVIE_RELEASE_YEAR, message = WRONG_MIN_MOVIE_RELEASE_YEAR_VALUE)
		int releaseYear
		) {

}
