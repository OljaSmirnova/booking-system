package com.att.interview.ticketbookingsystem.exception;

import com.att.interview.ticketbookingsystem.api.ServiceExceptionMessages;

@SuppressWarnings("serial")
public class MovieStateException extends IllegalStateException {
	
	public MovieStateException(String title, int releaseYear) {
		super(String.format(ServiceExceptionMessages.MOVIE_ALREADY_EXISTS , title, releaseYear));
	}

}
