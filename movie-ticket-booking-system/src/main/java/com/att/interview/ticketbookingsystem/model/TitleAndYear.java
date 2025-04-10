package com.att.interview.ticketbookingsystem.model;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.*;

@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TitleAndYear implements Serializable {
	private static final long serialVersionUID = 1L;
	@Column(name="movie_title")
	String title;
	@Column(name="movie_release_year")
	int year;

}
