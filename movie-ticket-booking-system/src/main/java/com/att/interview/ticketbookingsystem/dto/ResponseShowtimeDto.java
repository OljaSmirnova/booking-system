package com.att.interview.ticketbookingsystem.dto;

import static com.att.interview.ticketbookingsystem.api.ValidationConstants.*;

import java.time.LocalDateTime;

import jakarta.validation.constraints.*;

public record ResponseShowtimeDto(
		@NotEmpty(message = MISSING_MOVIE_TITLE) 
		String movieTitle,

		@NotEmpty(message = MISSING_THEATER_TITLE) 
		String theater,

		@NotNull(message = MISSING_MAX_NUMBER_SEATS) 
		@Positive(message = WRONG_NUMBER_MAX_SEATS) 
		int maxSeats,

		@NotNull(message = MISSING_START_TIME) 
		@Future(message = WRONG_START_TIME) 
		LocalDateTime startTime,

		@NotNull(message = MISSING_END_TIME) 
		@Future(message = WRONG_END_TIME) 
		LocalDateTime endTime) {
}
