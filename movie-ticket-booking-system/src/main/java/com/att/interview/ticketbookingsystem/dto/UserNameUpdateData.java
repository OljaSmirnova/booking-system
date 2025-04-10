package com.att.interview.ticketbookingsystem.dto;

import static com.att.interview.ticketbookingsystem.api.ValidationConstants.*;

import jakarta.validation.constraints.*;

public record UserNameUpdateData(
		@NotEmpty(message = MISSING_EMAIL)
		@Email(message = WRONG_EMAIL_FORMAT)
		String email,
		
		@NotEmpty(message = MISSING_USER_NAME)
		@Size(min = MIN_SIZE_NAME, max = MAX_SIZE_NAME, message = WRONG_SIZE_NAME)		
		String name
		) {

}
