package com.att.interview.ticketbookingsystem.exception;

import java.time.LocalDateTime;

import com.att.interview.ticketbookingsystem.api.ServiceExceptionMessages;

@SuppressWarnings("serial")
public class ShowtimeStateException extends IllegalStateException {
	
	public ShowtimeStateException(String title, String theater, LocalDateTime startTime) {
		super(String.format(ServiceExceptionMessages.SHOWTIME_ALREADY_EXISTS , title, theater, startTime));
	}

}
