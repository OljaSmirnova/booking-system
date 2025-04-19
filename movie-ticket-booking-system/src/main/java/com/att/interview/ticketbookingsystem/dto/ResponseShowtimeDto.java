package com.att.interview.ticketbookingsystem.dto;

import java.time.LocalDateTime;

public record ResponseShowtimeDto( 
		String movieTitle,
		String theater,
		int maxSeats,
		LocalDateTime startTime,
		LocalDateTime endTime) {
}
