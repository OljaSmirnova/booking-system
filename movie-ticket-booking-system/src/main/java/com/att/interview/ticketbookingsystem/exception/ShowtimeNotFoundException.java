package com.att.interview.ticketbookingsystem.exception;

import com.att.interview.exception.NotFoundException;
import com.att.interview.ticketbookingsystem.api.ServiceExceptionMessages;

@SuppressWarnings("serial")
public class ShowtimeNotFoundException extends NotFoundException {

	public ShowtimeNotFoundException() {
		super(ServiceExceptionMessages.SHOWTIME_NOT_FOUND);
	}

}
