package com.att.interview.ticketbookingsystem.model;

import com.att.interview.ticketbookingsystem.dto.MovieDto;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "movies")
@NoArgsConstructor
public class Movie {
	@EmbeddedId
	TitleAndYear titleYear;
	@Column(nullable = false)
    String genre;
	@Column(nullable = false)
    int duration;
	@Column(nullable = false)
    double rating;
    
    public MovieDto toDto() {
    	return new MovieDto(titleYear.getTitle(), genre, duration, rating, titleYear.getYear());
    }

}
