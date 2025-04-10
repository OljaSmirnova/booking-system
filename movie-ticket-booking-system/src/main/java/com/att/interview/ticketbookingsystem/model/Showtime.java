package com.att.interview.ticketbookingsystem.model;

import java.time.LocalDateTime;

import com.att.interview.ticketbookingsystem.dto.*;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "showtimes")
@NoArgsConstructor
public class Showtime {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	@ManyToOne
	@JoinColumns({@JoinColumn(name = "movie_title", nullable = false), @JoinColumn(name = "movie_release_year", nullable = false)})
	Movie movie;
	String theater;
	@Column(name = "start_time")
	LocalDateTime startTime;
	@Column(name = "end_time")
	LocalDateTime endTime;
	@Column(nullable = false)
	int maxSeats;
	
	public ResponseShowtimeDto toDto() {
		return new ResponseShowtimeDto(movie.getTitleYear().getTitle(), theater, maxSeats, startTime, endTime);
	}

}
