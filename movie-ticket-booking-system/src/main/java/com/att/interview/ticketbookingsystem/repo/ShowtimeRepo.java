package com.att.interview.ticketbookingsystem.repo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.*;

import com.att.interview.ticketbookingsystem.model.Showtime;

public interface ShowtimeRepo extends JpaRepository<Showtime, Long> {
	Optional<Showtime> findByMovie_TitleYear_TitleAndTheaterAndStartTime(String title, String theater, LocalDateTime startTime);
	List<Showtime> findByTheaterAndStartTimeBeforeAndEndTimeAfter(String theater, LocalDateTime endTime, LocalDateTime startTime);
	List<Showtime> findByMovie_TitleYear_TitleAndMovie_TitleYear_Year(String title, int year);
    List<Showtime> findByTheater(String theater);

}
