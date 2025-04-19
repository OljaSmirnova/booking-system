package com.att.interview.ticketbookingsystem.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.att.interview.ticketbookingsystem.dto.*;
import com.att.interview.ticketbookingsystem.service.ShowtimeService;

import static com.att.interview.ticketbookingsystem.api.UrlConstants.*;
import static com.att.interview.ticketbookingsystem.api.ValidationConstants.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("showtimes")
@RequiredArgsConstructor
@Slf4j
public class ShowtimeController {
	
	final ShowtimeService showtimeService;

    @PostMapping
    public ResponseShowtimeDto addShowtime(@RequestBody @Valid ShowtimeDto showtimeDto) {
        log.debug("addShowtime: received showtime data {}", showtimeDto);
        return showtimeService.addShowtime(showtimeDto);
    }

    @PutMapping
    public ResponseShowtimeDto updateShowtime(@RequestBody @Valid ShowtimeDto showtimeDto) {
        log.debug("updateShowtime: received showtime data {}", showtimeDto);
        return showtimeService.updateShowtimeDetails(showtimeDto);
    }

    @DeleteMapping
    public ResponseShowtimeDto deleteShowtime(@RequestBody @Valid ShowtimeDto showtimeDto) {
        log.debug("deleteShowtime: received showtime data {}", showtimeDto);
        return showtimeService.deleteShowtime(showtimeDto);
    }

    @GetMapping(MOVIE + "{title}/{releaseYear}")
    public List<ResponseShowtimeDto> getShowtimesByMovieTitle(@PathVariable @NotEmpty(message = MISSING_MOVIE_TITLE) String title, @PathVariable @NotNull(message = MISSING_MOVIE_RELEASE_YEAR) @Min(value = MIN_MOVIE_RELEASE_YEAR, 
    		message = WRONG_MIN_MOVIE_RELEASE_YEAR_VALUE) int releaseYear) {
        log.debug("getShowtimesByMovieTitle: title = {}, release year = {}", title, releaseYear);
        List<ResponseShowtimeDto> showtimes = showtimeService.getShowtimesByMovie(title, releaseYear);
        if (showtimes.isEmpty()) {
            log.warn("No showtimes found for movie title {}", title, releaseYear);
        }
        return showtimes;
    }

    @GetMapping(THEATER + "{theater}")
    public List<ResponseShowtimeDto> getShowtimesByTheater(
            @PathVariable @NotEmpty(message = MISSING_THEATER_TITLE) String theater) {
        log.debug("getShowtimesByTheater: theater = {}", theater);
        List<ResponseShowtimeDto> showtimes = showtimeService.getShowtimesByTheater(theater);
        if (showtimes.isEmpty()) {
            log.warn("No showtimes found for theater {}", theater);
        }
        return showtimes;
    }

}
