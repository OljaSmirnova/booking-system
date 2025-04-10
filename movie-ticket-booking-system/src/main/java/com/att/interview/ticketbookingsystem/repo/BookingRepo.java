package com.att.interview.ticketbookingsystem.repo;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import com.att.interview.ticketbookingsystem.model.Booking;

public interface BookingRepo extends JpaRepository<Booking, Long> {
	int countByShowtimeId(Long showtimeId);
	boolean existsByShowtimeIdAndSeatNumber(Long showtimeId, String seatNumber);
	@Query("SELECT b FROM Booking b WHERE b.user.email = :email")
	List<Booking> findByUserEmail(@Param("email") String email);


}
