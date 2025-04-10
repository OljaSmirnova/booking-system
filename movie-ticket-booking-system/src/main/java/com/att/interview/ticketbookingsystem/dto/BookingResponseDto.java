package com.att.interview.ticketbookingsystem.dto;

import java.time.LocalDateTime;

public record BookingResponseDto(
	    String userName,
	    String movieTitle,
	    String theater,
	    LocalDateTime movieSession,
	    String seatNumber,
	    double price
	) {}
