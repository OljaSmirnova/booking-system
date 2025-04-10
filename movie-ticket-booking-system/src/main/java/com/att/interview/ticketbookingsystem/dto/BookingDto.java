package com.att.interview.ticketbookingsystem.dto;

import static com.att.interview.ticketbookingsystem.api.ValidationConstants.*;

import java.time.LocalDateTime;

import jakarta.validation.constraints.*;

public record BookingDto(
		@NotEmpty(message = MISSING_USER_NAME)
		String userId,
		
		@NotEmpty(message = MISSING_MOVIE_TITLE)
		String movieTitle,
		
		@NotEmpty(message = MISSING_THEATER_TITLE)
		String theater,
		
		@NotNull(message = MISSING_START_TIME)
		LocalDateTime movieSession,
		
		@NotEmpty(message = MISSING_SEAT_NUMBER)
		@Pattern(regexp = SEAT_NUMBER_REGEX, message = WRONG_SEAT_NUMBER)
		String seatNumber,
		
		@NotNull(message = MISSING_TICKET_PRICE)
		@PositiveOrZero(message = WRONG_TICKET_PRICE_VALUE)
		double price
		) {

}
