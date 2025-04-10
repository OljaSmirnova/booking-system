package com.att.interview.ticketbookingsystem.service;

import java.util.List;

import com.att.interview.ticketbookingsystem.dto.*;

public interface BookingService {
	BookingResponseDto bookTicket(BookingDto bookingDto);
	List<BookingResponseDto> getBookingsByUser(String email);

}
