package com.att.interview.ticketbookingsystem.exception;

import com.att.interview.ticketbookingsystem.api.ServiceExceptionMessages;

@SuppressWarnings("serial")
public class UserStateExeption extends IllegalStateException {
	public UserStateExeption(String email) {
		super(String.format(ServiceExceptionMessages.USER_ALREADY_EXISTS ,email));
	}

}
