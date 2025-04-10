package com.att.interview.ticketbookingsystem.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.att.interview.ticketbookingsystem.model.Movie;
import com.att.interview.ticketbookingsystem.model.TitleAndYear;

public interface MoviesRepo extends JpaRepository<Movie, TitleAndYear> {
	Movie findByTitleAndReleaseYear(String title, int releaseYear);
	@Query("SELECT m.duration FROM Movie m WHERE m.titleYear = :id")
	int findDurationById(@Param("id") TitleAndYear titleYear);
	List<Movie> findByGenre(String genre);
	List<Movie> findByReleaseYear (int releaseYear);
	List<Movie> findByRatingGreaterThanEqualAndRatingLessThan(double minRating, double maxRating);

}
