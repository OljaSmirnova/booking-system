package com.att.interview.ticketbookingsystem.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.*;

import static com.att.interview.ticketbookingsystem.api.ValidationConstants.*;

public record ShowtimeDto(
		@NotEmpty(message = MISSING_MOVIE_TITLE)
	    String movieTitle,
	    
		@NotNull(message = MISSING_MOVIE_RELEASE_YEAR)
	    int movieReleaseYear,

	    @NotEmpty(message = MISSING_THEATER_TITLE)
	    String theater,
	    
	    @NotNull(message = MISSING_MAX_NUMBER_SEATS)
		@Positive(message = WRONG_NUMBER_MAX_SEATS)
		int maxSeats,

	    @NotNull(message = MISSING_START_TIME)
	    @Future(message = WRONG_START_TIME)
	    LocalDateTime startTime

	    ) {

}
