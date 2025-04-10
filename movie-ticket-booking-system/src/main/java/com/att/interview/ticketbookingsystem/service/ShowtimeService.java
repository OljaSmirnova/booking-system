package com.att.interview.ticketbookingsystem.service;

import java.util.List;

import com.att.interview.ticketbookingsystem.dto.*;

public interface ShowtimeService {
	ResponseShowtimeDto addShowtime(ShowtimeDto showtimeDto);
	ResponseShowtimeDto updateShowtimeDetails(ShowtimeDto showtimeDto);
	ResponseShowtimeDto deleteShowtime(ShowtimeDto showtimeDto);
	List<ResponseShowtimeDto> getShowtimesByMovieTile(String movieTitle);
	List<ResponseShowtimeDto> getShowtimesByTheater(String theater);
	

}
