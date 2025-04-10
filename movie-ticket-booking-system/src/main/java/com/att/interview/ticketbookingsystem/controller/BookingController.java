package com.att.interview.ticketbookingsystem.controller;

import static com.att.interview.ticketbookingsystem.api.ValidationConstants.*;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.att.interview.ticketbookingsystem.dto.*;
import com.att.interview.ticketbookingsystem.service.*;

import jakarta.validation.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("bookings")
@RequiredArgsConstructor
@Slf4j
public class BookingController {

	final BookingService bookingService;

	
	@PostMapping
	BookingResponseDto bookTicket(@RequestBody @Valid BookingDto bookingDto) {
		log.debug("bookTicket: received booking data {}", bookingDto);
		return bookingService.bookTicket(bookingDto);
	}
	
	@GetMapping("/{email}")
	List<BookingResponseDto> getBookingsByUser(@PathVariable @NotEmpty(message = MISSING_EMAIL) @Email(message = WRONG_EMAIL_FORMAT) String email){
		log.debug("getBookingsByUser: email = {}", email);
		return bookingService.getBookingsByUser(email);
	}
	

}
