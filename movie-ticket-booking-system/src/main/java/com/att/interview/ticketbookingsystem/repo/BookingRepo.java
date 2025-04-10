package com.att.interview.ticketbookingsystem.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.att.interview.ticketbookingsystem.model.Booking;

public interface BookingRepo extends JpaRepository<Booking, Long> {
	int countByShowtimeId(Long showtimeId);
	boolean existsByShowtimeIdAndSeatNumber(Long showtimeId, String seatNumber);
	List<Booking> findByUserId(String userId); 

}
