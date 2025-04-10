package com.att.interview.ticketbookingsystem.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.att.interview.ticketbookingsystem.dto.*;
import com.att.interview.ticketbookingsystem.exception.*;
import com.att.interview.ticketbookingsystem.model.*;
import com.att.interview.ticketbookingsystem.repo.*;

import static com.att.interview.ticketbookingsystem.api.ServiceExceptionMessages.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImpl implements BookingService {
	final BookingRepo bookingRepo;
	final UsersRepo userRepo;
	final ShowtimeRepo showtimeRepo;

	@Override
	public BookingResponseDto bookTicket(BookingDto dto) {
		User user = userRepo.findById(dto.userId()).orElseThrow(() -> new UserNotFoundException());

		Showtime showtime = showtimeRepo.findByMovie_TitleYear_TitleAndTheaterAndStartTime(dto.movieTitle(), dto.theater(), 
				dto.movieSession()).orElseThrow(() -> new ShowtimeNotFoundException());

		if (bookingRepo.existsByShowtimeIdAndSeatNumber(showtime.getId(), dto.seatNumber())) {
			throw new IllegalArgumentException(SEAT_ALREADY_BOOKED);
		}

		int bookedSeats = bookingRepo.countByShowtimeId(showtime.getId());
		if (bookedSeats >= showtime.getMaxSeats()) {
			throw new IllegalStateException(ALL_SEATS_ALREADY_BOOKED);
		}

		Booking booking = new Booking();
		booking.setUser(user);
		booking.setShowtime(showtime);
		booking.setSeatNumber(dto.seatNumber());
		booking.setPrice(dto.price());
		
		bookingRepo.save(booking);
		BookingResponseDto responseDto = booking.toResponseDTO();
		log.debug("booking: {} has been saved", responseDto);
		return responseDto;
	}

	@Override
	public List<BookingResponseDto> getBookingsByUser(String email) {
		List<Booking> booking = bookingRepo.findByUserEmail(email);
		if(booking.isEmpty()) {
			log.warn("booking with user id {} has no sessions", email);
		} else {
			log.debug("booking with id {} has {} sessions", email, booking.size());
		}
		return booking.stream().map(Booking::toResponseDTO).toList();
	}

}
