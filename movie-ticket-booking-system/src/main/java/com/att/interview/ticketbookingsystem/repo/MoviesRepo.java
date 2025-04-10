package com.att.interview.ticketbookingsystem.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.att.interview.ticketbookingsystem.model.Movie;
import com.att.interview.ticketbookingsystem.model.TitleAndYear;

public interface MoviesRepo extends JpaRepository<Movie, TitleAndYear> {
	Movie findByTitleYear_TitleAndTitleYear_Year(String title, int year);
	@Query("SELECT m.duration FROM Movie m WHERE m.titleYear = :id")
	int findDurationById(@Param("id") TitleAndYear titleYear);
	List<Movie> findByGenre(String genre);
	List<Movie> findByTitleYear_Year(int year);
	List<Movie> findByRatingGreaterThanEqualAndRatingLessThan(double minRating, double maxRating);

}
