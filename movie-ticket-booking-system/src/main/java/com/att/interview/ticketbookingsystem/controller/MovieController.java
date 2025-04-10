package com.att.interview.ticketbookingsystem.controller;

import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import com.att.interview.ticketbookingsystem.dto.MovieDto;
import com.att.interview.ticketbookingsystem.dto.MovieTitleAndYearData;
import com.att.interview.ticketbookingsystem.service.MovieService;

import static com.att.interview.ticketbookingsystem.api.UrlConstants.*;
import static com.att.interview.ticketbookingsystem.api.ValidationConstants.*;

import java.util.List;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("movies")
@RequiredArgsConstructor
@Slf4j
public class MovieController {
	
	final MovieService movieService;
	
	@PostMapping
	MovieDto addMovie(@RequestBody @Valid MovieDto movieDto) {
		log.debug("addMovie: received movie data {}", movieDto);
		return movieService.addMovie(movieDto);
	}
	
	@PutMapping
	MovieDto updateMovieData(@RequestBody @Valid MovieDto movieDto) {
		log.debug("updateMovie: received movie data {}", movieDto);
		return movieService.updateMovieData(movieDto);
	}
	
	@DeleteMapping("{movieData}")
	MovieDto deleteMovie(@RequestBody @Valid MovieTitleAndYearData movieData) {
		log.debug("deleteMovie: movie with title {} and release year {}", movieData.title(), movieData.releaseYear());
		return movieService.deleteMovie(movieData.title(), movieData.releaseYear());
	}
	
	@GetMapping("{movieData}")
	MovieDto getMovieData(@RequestBody @Valid MovieTitleAndYearData movieData) {
		log.debug("getMovieData: movie with title {} and release year {}", movieData.title(), movieData.releaseYear());
		return movieService.getMovieData(movieData.title(), movieData.releaseYear());
	}
	
	@GetMapping()
	List<MovieDto> getMovies() {
		List<MovieDto> res = movieService.getMovies();
		if(res.isEmpty()) {
			log.warn("getMoviese: list of movies is empty");
		}
		else {
			log.trace("getMoviesByGenre: list of movies is fully");
		}
		return res;
	}
	
	@GetMapping(MOVIES_GENRE + "{genre}")
	List<MovieDto> getMoviesByGenre(@PathVariable @NotBlank(message = MISSING_MOVIE_GENRE) String genre) {
		List<MovieDto> res = movieService.getMoviesByGenre(genre);
		if(res.isEmpty()) {
			log.warn("getMoviesByGenre: no movies by genre {}", genre );
		}
		else {
			log.trace("getMoviesByGenre: received movies by genre {}", genre);
		}
		return res ;
	}
	
	@GetMapping(MOVIES_RATING + "{rating}")
	List<MovieDto> getMoviesByRating(@PathVariable @NotNull(message = MISSING_MOVIE_RATING) @Min(value = MIN_RATING_INTEGER, 
	message = WRONG_MIN_MOVIE_RATING_VALUE + MIN_RATING_INTEGER) @Max(value = MAX_RATING_INTEGER, 
	message = WRONG_MAX_MOVIE_RATING_VALUE + MAX_RATING_INTEGER) int rating) {
		List<MovieDto> res = movieService.getMoviesByRating(rating);
		if(res.isEmpty()) {
			log.warn("getMoviesByRating: no movies by rating {}", rating );
		}
		else {
			log.trace("getMoviesByRating: received movies by rating {}", rating);
		}
		return res ;
	}
	
	@GetMapping(MOVIES_RELEASE_YEAR + "{releaseYear}")
	List<MovieDto> getMoviesByReleaseYear(@PathVariable @NotNull(message = MISSING_MOVIE_RELEASE_YEAR) @Min(value = MIN_MOVIE_RELEASE_YEAR, 
	message = WRONG_MIN_MOVIE_RELEASE_YEAR_VALUE) int releaseYear){
		List<MovieDto> res = movieService.getMoviesByReleaseYear(releaseYear);
		if(res.isEmpty()) {
			log.warn("getMoviesByReleaseYear: no movies by release year {}", releaseYear );
		}
		else {
			log.trace("getMoviesByReleaseYear: received movies by rlease year {}", releaseYear);
		}
		return res ;
	}

}
