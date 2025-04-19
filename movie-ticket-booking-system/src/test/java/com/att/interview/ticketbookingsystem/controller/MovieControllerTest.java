package com.att.interview.ticketbookingsystem.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.att.interview.exception.controller.ExceptionController;
import com.att.interview.ticketbookingsystem.api.ValidationConstants;
import com.att.interview.ticketbookingsystem.config.TestSecurityConfig;
import com.att.interview.ticketbookingsystem.dto.MovieDto;
import com.att.interview.ticketbookingsystem.exception.MovieNotFoundException;
import com.att.interview.ticketbookingsystem.exception.MovieStateException;
import com.att.interview.ticketbookingsystem.service.MovieService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static com.att.interview.ticketbookingsystem.api.UrlConstants.*;
import static com.att.interview.ticketbookingsystem.api.ServiceExceptionMessages.*;

@WebMvcTest(MovieController.class)
@Import({TestSecurityConfig.class, ExceptionController.class})
class MovieControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper mapper;
	
	@MockitoBean
	MovieService movieService;
	
	private static final String HOST = "http://localhost:8000/";
	private static final String URL_MOVIES = HOST + MOVIES;
	private static final String URL_MOVIES_GENRE = URL_MOVIES + MOVIES_GENRE;
	private static final String URL_MOVIES_RATING = URL_MOVIES + MOVIES_RATING;
	private static final String URL_MOVIES_RELEASE_YEAR = URL_MOVIES + MOVIES_RELEASE_YEAR;
	private static final String GENER_1 = "Sci-Fi";
	private static final String MOVIE_DATA = "/Inception/2010";
	private static final String TITLE_1 = "Inception";
	private static final int RATING_1 = 8;
	private static final int YEAR_1 = 2010;
	private MovieDto movieDto = new MovieDto("Inception", "Sci-Fi", 148, 8.8, 2010);
	private MovieDto movieMissingFields = new MovieDto("", "Sci-Fi", 148, 8.8, null);
	private MovieDto movieWrongFields = new MovieDto("Inception", "Sci-Fi", 148, 8.8, 1870);
	private MovieDto updatedMovie = new MovieDto("Inception", "Sci-Fi", 148, 9.0, 2010);
	private List<MovieDto> movies = Arrays.asList(
            new MovieDto("Inception", "Sci-Fi", 148, 8.8, 2010),
            new MovieDto("The Dark Knight", "Action", 152, 9.0, 2008),
            new MovieDto("The Matrix", "Sci-Fi", 136, 8.7, 1999)       
    );
	String[] errorMessageMovieWrongFields = {ExceptionController.JSON_TYPE_MISMATCH_MESSAGE};
	String[] errorMessagesMovieMissingFields = { ValidationConstants.MISSING_MOVIE_TITLE,
			ValidationConstants.MISSING_MOVIE_RELEASE_YEAR
			};
	String[] errorMessagesMovieWrongFields = { ValidationConstants.WRONG_MIN_MOVIE_RELEASE_YEAR_VALUE, 
			};

	@Test
	void addMovie_ReturnsCreatedMovie() throws Exception {
		when(movieService.addMovie(movieDto)).thenReturn(movieDto);

		String movieJSON = mapper.writeValueAsString(movieDto);
		String response = mockMvc.perform(post(URL_MOVIES).contentType(MediaType.APPLICATION_JSON).content(movieJSON))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		
		assertEquals(movieJSON, response);
	}
	
	@Test
	void updateMovie_ReturnsUpdatedMovie() throws Exception {
	    when(movieService.updateMovieData(updatedMovie)).thenReturn(updatedMovie);

	    String movieJSON = mapper.writeValueAsString(updatedMovie);

	    String response = mockMvc.perform(put(URL_MOVIES)
	                    .contentType(MediaType.APPLICATION_JSON)
	                    .content(movieJSON))
	            .andExpect(status().isOk())
	            .andReturn().getResponse().getContentAsString();

	    assertEquals(movieJSON, response);
	}
	
	@Test
	void deleteMovie_ReturnsDeletedMovie() throws Exception {
	    when(movieService.deleteMovie(movieDto.title(), movieDto.releaseYear())).thenReturn(movieDto);

	    String movieJSON = mapper.writeValueAsString(movieDto);
	    String response = mockMvc.perform(delete(URL_MOVIES + MOVIE_DATA))
	            .andExpect(status().isOk())
	            .andReturn().getResponse().getContentAsString();

	    assertEquals(movieJSON, response);
	}
	@Test
	void getMovie_ReturnsMovie() throws Exception {
	    when(movieService.getMovieData(movieDto.title(), movieDto.releaseYear())).thenReturn(movieDto);
	    
	    String movieJSON = mapper.writeValueAsString(movieDto);
	    String response = mockMvc.perform(get(URL_MOVIES + MOVIE_DATA))
	            .andExpect(status().isOk())
	            .andReturn().getResponse().getContentAsString();

	    assertEquals(movieJSON, response);
	}

	
	@Test
	void getMovies_ReturnsMoviesList() throws Exception {
	    when(movieService.getMovies()).thenReturn(movies);

	    String response = mockMvc.perform(get(URL_MOVIES))
	            .andExpect(status().isOk())
	            .andReturn().getResponse().getContentAsString();

	    assertEquals(mapper.writeValueAsString(movies), response);
	}
	
	@Test
	void getMoviesByGenre_ReturnsMoviesByGenre() throws Exception {
	    when(movieService.getMoviesByGenre(GENER_1)).thenReturn(movies);

	    String response = mockMvc.perform(get(URL_MOVIES_GENRE + GENER_1))
	            .andExpect(status().isOk())
	            .andReturn().getResponse().getContentAsString();

	    assertEquals(mapper.writeValueAsString(movies), response);
	}
	
	@Test
	void getMoviesByRating_ReturnsMoviesByRating() throws Exception {
	    when(movieService.getMoviesByRating(RATING_1)).thenReturn(movies);

	    String response = mockMvc.perform(get(URL_MOVIES_RATING + RATING_1))
	            .andExpect(status().isOk())
	            .andReturn().getResponse().getContentAsString();

	    assertEquals(mapper.writeValueAsString(movies), response);
	}
	
	@Test
	void getMoviesByReleaseYear_ReturnsMoviesByReleaseYear() throws Exception {
	    when(movieService.getMoviesByReleaseYear(YEAR_1)).thenReturn(movies);

	    String response = mockMvc.perform(get(URL_MOVIES_RELEASE_YEAR + YEAR_1))
	            .andExpect(status().isOk())
	            .andReturn().getResponse().getContentAsString();

	    assertEquals(mapper.writeValueAsString(movies), response);
	}
	
	@Test
	void addMovie_MissingFields_ReturnsAlert() throws Exception {
		String movieJSON = mapper.writeValueAsString(movieMissingFields);
		String response = mockMvc
				.perform(post(URL_MOVIES).contentType(MediaType.APPLICATION_JSON).content(movieJSON))
				.andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();
		assertErrorMessages(errorMessagesMovieMissingFields, response);
	}

	private void assertErrorMessages(String[] expectedMessages, String response) {
		Arrays.sort(expectedMessages);
		String [] actualMessages = response.split(";");
		Arrays.sort(actualMessages);
		assertArrayEquals(expectedMessages, actualMessages);
		
	}

	@Test
	void addMovie_WrongFields_ReturnsAlert() throws Exception {
		String movieJSON = mapper.writeValueAsString(movieWrongFields);
		String response = mockMvc
				.perform(post(URL_MOVIES).contentType(MediaType.APPLICATION_JSON).content(movieJSON))
				.andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();
		assertErrorMessages(errorMessagesMovieWrongFields, response);
	}
	
	@Test
	void addMovie_MovieAlreadyExists_ReturnsAlert() throws Exception {
		when(movieService.addMovie(movieDto)).thenThrow(new MovieStateException(TITLE_1, YEAR_1));
		
		String movieJSON = mapper.writeValueAsString(movieDto);
		String response = mockMvc.perform(post(URL_MOVIES).contentType(MediaType.APPLICATION_JSON).content(movieJSON))
				.andExpect(status().isBadRequest())
				.andReturn().getResponse().getContentAsString();
		
		assertEquals(String.format(MOVIE_ALREADY_EXISTS , TITLE_1, YEAR_1), response);
	}
	
	@Test
	void deleteMovie_MovieNotFound_ReturnsAlert() throws Exception {
		when(movieService.deleteMovie(TITLE_1, YEAR_1)).thenThrow(new MovieNotFoundException());
		
		String actualJSON = mockMvc.perform(delete(URL_MOVIES + MOVIE_DATA))
				.andExpect(status().isNotFound()).andReturn().getResponse().getContentAsString();
		
		assertEquals(MOVIE_NOT_FOUND, actualJSON);
	}







}
