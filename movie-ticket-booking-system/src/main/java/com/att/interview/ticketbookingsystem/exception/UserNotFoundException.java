package com.att.interview.ticketbookingsystem.exception;

import com.att.interview.exception.NotFoundException;
import com.att.interview.ticketbookingsystem.api.ServiceExceptionMessages;

@SuppressWarnings("serial")
public class UserNotFoundException extends NotFoundException {

	public UserNotFoundException() {
		super(ServiceExceptionMessages.USER_NOT_FOUND);
	}

}
