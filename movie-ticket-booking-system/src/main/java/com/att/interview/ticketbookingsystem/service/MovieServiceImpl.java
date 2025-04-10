package com.att.interview.ticketbookingsystem.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.att.interview.ticketbookingsystem.dto.MovieDto;
import com.att.interview.ticketbookingsystem.exception.*;
import com.att.interview.ticketbookingsystem.model.*;
import com.att.interview.ticketbookingsystem.repo.MoviesRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieServiceImpl implements MovieService {
	
	final MoviesRepo moviesRepo;

	@Override
	public MovieDto addMovie(MovieDto dto) {
		moviesRepo.findById(new TitleAndYear(dto.title(), dto.releaseYear()))
		.ifPresent(existingMovie -> {
            throw new MovieStateException(dto.title(), dto.releaseYear());
            });
		
        Movie movie = new Movie();
        movie.setTitleYear(new TitleAndYear(dto.title(), dto.releaseYear()));
        movie.setDuration(dto.duration());
        movie.setGenre(dto.genre());
        movie.setRating(dto.rating());

        moviesRepo.save(movie);
        log.debug("Movie: has been saved", dto);
        
        return movie.toDto();
	}

	@Override
	public MovieDto updateMovieData(MovieDto dto) {
		Movie movie = moviesRepo.findById(new TitleAndYear(dto.title(), dto.releaseYear())).orElseThrow(() ->  new MovieNotFoundException());
		
		movie.setDuration(dto.duration());
		movie.setGenre(dto.genre());
        movie.setRating(dto.rating());
        moviesRepo.save(movie);
        
        log.debug("Movie: {} has been updated", dto);
        
        return movie.toDto();
	}

	@Override
	public MovieDto deleteMovie(String title, int releaseYear) {
		Movie movie = moviesRepo.findById(new TitleAndYear(title, releaseYear)).orElseThrow(() ->  new MovieNotFoundException());
		
		moviesRepo.delete(movie);
		log.debug("Movie: with title {} and release year {} has been deleted", title, releaseYear);
		
		return movie.toDto();
	}

	@Override
	public MovieDto getMovieData(String title, int releaseYear) {
		Movie movie = moviesRepo.findById(new TitleAndYear(title, releaseYear)).orElseThrow(() ->  new MovieNotFoundException());

		log.debug("Movie: with title {} and release year {} has been deleted", title, releaseYear);
		return movie.toDto();
	}

	@Override
	public List<MovieDto> getMovies() {
	       List<Movie> movies = moviesRepo.findAll();
	        if (movies.isEmpty()) {
	            log.warn("Movies not found");
	        } else {
	            log.debug("Find {} movies", movies.size());
	        }
		return movies.stream().map(Movie::toDto).collect(Collectors.toList());
	}

	@Override
	public List<MovieDto> getMoviesByGenre(String genre) {
        List<Movie> movies = moviesRepo.findByGenre(genre);
        if (movies.isEmpty()) {
            log.warn("Movies with genre{} not found", genre);
        } else {
            log.debug("Found {} movies with genre {}", movies.size(), genre);
        }

        return movies.stream().map(Movie::toDto).collect(Collectors.toList());
	}

	@Override
	public List<MovieDto> getMoviesByRating(int rating) {
		double minRating = rating;
        double maxRating = rating + 1.0;
        List<Movie> movies = moviesRepo.findByRatingGreaterThanEqualAndRatingLessThan(minRating, maxRating);
        if (movies.isEmpty()) {
            log.warn("Movies with rating{} not found", rating);
        } else {
            log.debug("Found {} movies with rating {}", movies.size(), rating);
        }

        return movies.stream().map(Movie::toDto).collect(Collectors.toList());
	}

	@Override
	public List<MovieDto> getMoviesByReleaseYear(int releaseYear) {
        List<Movie> movies = moviesRepo.findByReleaseYear(releaseYear);
        if (movies.isEmpty()) {
            log.warn("Movies with year of release {} not found", releaseYear);
        } else {
            log.debug("Found {} movies with release year {}", movies.size(), releaseYear);
        }

        return movies.stream().map(Movie::toDto).collect(Collectors.toList());
	}

}
