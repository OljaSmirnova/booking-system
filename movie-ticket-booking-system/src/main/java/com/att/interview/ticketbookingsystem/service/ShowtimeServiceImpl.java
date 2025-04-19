package com.att.interview.ticketbookingsystem.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.att.interview.ticketbookingsystem.dto.*;
import com.att.interview.ticketbookingsystem.exception.MovieNotFoundException;
import com.att.interview.ticketbookingsystem.exception.ShowtimeNotFoundException;
import com.att.interview.ticketbookingsystem.exception.ShowtimeStateException;
import com.att.interview.ticketbookingsystem.model.Movie;
import com.att.interview.ticketbookingsystem.model.Showtime;
import com.att.interview.ticketbookingsystem.model.TitleAndYear;
import com.att.interview.ticketbookingsystem.repo.MoviesRepo;
import com.att.interview.ticketbookingsystem.repo.ShowtimeRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShowtimeServiceImpl implements ShowtimeService {
	
	final ShowtimeRepo showtimeRepo;
	final MoviesRepo moviesRepo;

	@Override
	public ResponseShowtimeDto addShowtime(ShowtimeDto dto) {
        LocalDateTime startTime = dto.startTime();
        
        @SuppressWarnings("unused")
		Showtime showtimeExtends = showtimeRepo.findByMovie_TitleYear_TitleAndTheaterAndStartTime(
                dto.movieTitle(), dto.theater(), dto.startTime()).orElseThrow(() -> 
                new ShowtimeStateException(dto.movieTitle(), dto.theater(), startTime));
        
        Movie movie = moviesRepo.findById(new TitleAndYear(dto.movieTitle(), dto.movieReleaseYear()))
        		.orElseThrow(() -> new MovieNotFoundException());
        LocalDateTime endTime = startTime.plusMinutes(moviesRepo.findDurationById(movie.getTitleYear()));		
        		
        List<Showtime> conflictingShowtimes = showtimeRepo.findByTheaterAndStartTimeBeforeAndEndTimeAfter(
            dto.theater(), startTime, endTime);
        
        if (!conflictingShowtimes.isEmpty()) {
            throw new IllegalArgumentException("The session time overlaps with another session in this theatre");
        }
        
        Showtime showtime = new Showtime();
        showtime.setMovie(movie);
        showtime.setTheater(dto.theater());
        showtime.setStartTime(startTime);
        showtime.setEndTime(endTime);
        showtime.setMaxSeats(dto.maxSeats());

        showtimeRepo.save(showtime);
        log.debug("Showtime for movie: {} has been added", dto.movieTitle());
        
        return showtime.toDto();
    }

	@Override
	public ResponseShowtimeDto updateShowtimeDetails(ShowtimeDto dto) {
		Showtime showtime = showtimeRepo.findByMovie_TitleYear_TitleAndTheaterAndStartTime(
	            dto.movieTitle(), dto.theater(), dto.startTime()).orElseThrow(() -> new ShowtimeNotFoundException());

	        LocalDateTime startTime = dto.startTime();
	        
	        Movie movie = moviesRepo.findById(new TitleAndYear(dto.movieTitle(), dto.movieReleaseYear()))
	        		.orElseThrow(() -> new MovieNotFoundException());
	        LocalDateTime endTime = startTime.plusMinutes(moviesRepo.findDurationById(movie.getTitleYear()));

	        List<Showtime> conflictingShowtimes = showtimeRepo.findByTheaterAndStartTimeBeforeAndEndTimeAfter(
	            dto.theater(), endTime, startTime);
	        
	        if (!conflictingShowtimes.isEmpty()) {
	            throw new IllegalArgumentException("The session time overlaps with another session in this theatre");
	        }

	        showtime.setStartTime(startTime);
	        showtime.setEndTime(endTime);
	        showtime.setMaxSeats(dto.maxSeats());

	        showtimeRepo.save(showtime);
	        log.debug("Showtime for film: {} in theater {} starting {} has been updated", dto.movieTitle(), dto.theater(), dto.startTime());
	        
	        return showtime.toDto();
	}

	@Override
	public ResponseShowtimeDto deleteShowtime(ShowtimeDto dto) {
		Showtime showtime = showtimeRepo.findByMovie_TitleYear_TitleAndTheaterAndStartTime(
	            dto.movieTitle(), dto.theater(), dto.startTime()).orElseThrow(() -> new ShowtimeNotFoundException());
		
		ResponseShowtimeDto res = showtime.toDto();

	        showtimeRepo.delete(showtime);
	        log.debug("Showtime for film: {} in theater {} starting {} has been removed", dto.movieTitle(), dto.theater(), dto.startTime());

	        return res;
	}

	@Override
	public List<ResponseShowtimeDto> getShowtimesByMovie(String movieTitle, int movieReleaseYear) {
        List<Showtime> showtimes = showtimeRepo.findByMovie_TitleYear_TitleAndMovie_TitleYear_Year(movieTitle, movieReleaseYear);
        if (showtimes.isEmpty()) {
            log.warn("Showtimes for movie {} not found", movieTitle);
        } else {
            log.debug("Found {} sessions for movie {}", showtimes.size(), movieTitle);
        }

        return showtimes.stream().map(Showtime::toDto).collect(Collectors.toList());
	}

	@Override
	public List<ResponseShowtimeDto> getShowtimesByTheater(String theater) {
        List<Showtime> showtimes = showtimeRepo.findByTheater(theater);
        if (showtimes.isEmpty()) {
            log.warn("Showtimes for theatre {} not found", theater);
        } else {
            log.debug("Found {} sessions for theater {}", showtimes.size(), theater);
        }

        return showtimes.stream().map(Showtime::toDto).collect(Collectors.toList());
	}

}
