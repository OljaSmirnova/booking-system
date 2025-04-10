package com.att.interview.ticketbookingsystem.api;

public interface ValidationConstants {
	String MISSING_MOVIE_TITLE = "Missing movie title";
	String MISSING_MOVIE_GENRE = "Missing movie genre";
	String MISSING_MOVIE_DURATION = "Missing movie duration";
	String WRONG_DURATION = "Duration must be a positive number";
	String MISSING_MOVIE_RATING = "Missing movie rating";
	String MISSING_MOVIE_RELEASE_YEAR = "Missing movie release year";
	int MIN_RATING_INTEGER = 0;
	int MAX_RATING_INTEGER = 10;
	int MIN_MOVIE_RELEASE_YEAR = 1900;
	String MIN_MOVIE_RATING = "0.0";
	String MAX_MOVIE_RATING = "10.0";
	String WRONG_MIN_MOVIE_RATING_VALUE = "Movie rating must be greater or equal ";
	String WRONG_MAX_MOVIE_RATING_VALUE = "Movie rating must be less or equal ";
	String WRONG_MIN_MOVIE_RELEASE_YEAR_VALUE = "Year cannot be less than " + MIN_MOVIE_RELEASE_YEAR;
	
	
	String MISSING_THEATER_TITLE = "Missing theater title";
	String MISSING_START_TIME = "Missing start movie time";
	String MISSING_END_TIME = "Missing end movie time";
	String WRONG_START_TIME = "Start time must be in the future";
	String WRONG_END_TIME = "End time must be in the future";
	String MISSING_MAX_NUMBER_SEATS = "Missing max number seats";
	String WRONG_NUMBER_MAX_SEATS = "Number of seats must be a positive number"; 
	
	String MISSING_USER_NAME = "Missing user name";
	String MISSING_EMAIL = "Missing user email";
	String MISSING_PASSWORD = "Missing user password";
	String MISSING_USER_ROLE = "Missing user role";
	int MIN_SIZE_NAME = 2;
	int MAX_SIZE_NAME = 50;
	String WRONG_SIZE_NAME = "Name must be between " + MIN_SIZE_NAME + " and " + MAX_SIZE_NAME + " characters";
	String WRONG_EMAIL_FORMAT = "Wrong email format";
	int MIN_SIZE_PASSWORD = 6;
	String WRONG_SIZE_PASSWORD = "Password must be great or equal " + MIN_SIZE_PASSWORD;
	
	String MISSING_SEAT_NUMBER = "Missing seat number";
	String SEAT_NUMBER_REGEX = "\"^[A-Z]\\\\d+$\"";
	String WRONG_SEAT_NUMBER = "Seat number must start with a capital letter followed by digits";
	String MISSING_TICKET_PRICE = "Missing ticket price";
	String WRONG_TICKET_PRICE_VALUE = "Ticket price must be a positive number or zero";


}
