package com.att.interview.ticketbookingsystem.api;

public interface ServiceExceptionMessages {
	String ALL_SEATS_ALREADY_BOOKED = "Maximum number of seats reached for this showtime";
	String SEAT_ALREADY_BOOKED = "Seat already booked for this showtime";
	String SHOWTIME_NOT_FOUND = "Showtime not found";
	String SHOWTIME_ALREADY_EXISTS = "Showtime with movie title %s, theater %s and start time %d already exists";
	String USER_NOT_FOUND = "User not found";
	String USER_ALREADY_EXISTS = "User with email %s already exists.";
	String MOVIE_ALREADY_EXISTS = "Movie with title %s and release year %d already exists";
	String MOVIE_NOT_FOUND = "Movie not found";
}
