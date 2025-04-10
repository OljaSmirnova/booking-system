package com.att.interview.ticketbookingsystem.exception;

import com.att.interview.exception.NotFoundException;
import com.att.interview.ticketbookingsystem.api.ServiceExceptionMessages;

@SuppressWarnings("serial")
public class MovieNotFoundException extends NotFoundException {

	public MovieNotFoundException() {
		
		super(ServiceExceptionMessages.MOVIE_NOT_FOUND);
	}

}
