package com.att.interview.ticketbookingsystem.dto;

import jakarta.validation.constraints.*;

import static com.att.interview.ticketbookingsystem.api.ValidationConstants.*;

public record UserDto(
		@NotNull(message = MISSING_USER_NAME)
		@Size(min = MIN_SIZE_NAME, max = MAX_SIZE_NAME, message = WRONG_SIZE_NAME)
		String name,
		@NotEmpty(message = MISSING_EMAIL)
		@Email(message = WRONG_EMAIL_FORMAT)
		String email,
		@NotNull(message = MISSING_PASSWORD) 
		@Size(min=MIN_SIZE_PASSWORD, message = WRONG_SIZE_PASSWORD)
		String password,
		@NotNull(message = MISSING_USER_ROLE)
		Role role
		) {

}
