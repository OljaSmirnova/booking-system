package com.att.interview.ticketbookingsystem.service;

import java.util.List;

import com.att.interview.ticketbookingsystem.dto.MovieDto;

public interface MovieService {
	MovieDto addMovie(MovieDto movieDto);
	MovieDto updateMovieData(MovieDto movieDto);
	MovieDto deleteMovie(String title, int releaseYear);
	MovieDto getMovieData(String title, int releaseYear);
	List<MovieDto> getMovies();
	List<MovieDto> getMoviesByGenre(String genre);
	List<MovieDto> getMoviesByRating(int rating);
	List<MovieDto> getMoviesByReleaseYear(int releaseYear);

}
