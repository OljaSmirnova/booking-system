package com.att.interview.ticketbookingsystem.dto;

import static com.att.interview.ticketbookingsystem.api.ValidationConstants.*;

import jakarta.validation.constraints.*;

public record PasswordUpdateData(
		@NotEmpty(message = MISSING_EMAIL)
		@Email(message = WRONG_EMAIL_FORMAT)
		String email, 
		
		@NotNull(message = MISSING_PASSWORD) 
		@Size(min=MIN_SIZE_PASSWORD, message = WRONG_SIZE_PASSWORD)
		String password
		) {

}
