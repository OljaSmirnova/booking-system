package com.att.interview.ticketbookingsystem.controller;

import static com.att.interview.ticketbookingsystem.api.ServiceExceptionMessages.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
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
import com.att.interview.ticketbookingsystem.dto.*;
import com.att.interview.ticketbookingsystem.exception.*;
import com.att.interview.ticketbookingsystem.service.ShowtimeService;
import com.fasterxml.jackson.databind.ObjectMapper;


import static com.att.interview.ticketbookingsystem.api.UrlConstants.*;

@WebMvcTest(ShowtimeController.class)
@Import({TestSecurityConfig.class, ExceptionController.class})
class ShowtimeControllerTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper mapper;
	
	@MockitoBean
	ShowtimeService service;
	
	private static final String HOST = "http://localhost:8000/";
	private static final String URL_SHOWTIME = HOST + SHOWTIMES;
	private static final String URL_SHOWTIMES_BY_MOVIE = URL_SHOWTIME + MOVIE;
	private static final String URL_SHOWTIMES_BY_THEATER = URL_SHOWTIME + THEATER;
	
	private static final String SHOWTIME_DATA = "/Inception/2010";
	private static final String TITLE_1 = "Inception";
	private static final String TITLE_2 = "The Dark Knight";
	private static final int YEAR_1 = 2010;
	private static final String THEATER_1 = "A-20";
	private static final String THEATER_2 = "B-30";
	private static final LocalDateTime START_TIME_1 = LocalDateTime.of(2025, 5, 12, 15, 40);
	private static final LocalDateTime END_TIME_1 = LocalDateTime.of(2025, 5, 12, 17, 50);
	private static final LocalDateTime START_TIME_2 = LocalDateTime.of(2025, 5, 12, 16, 40);
	private static final LocalDateTime END_TIME_2 = LocalDateTime.of(2025, 5, 12, 18, 50);
	private static final LocalDateTime START_TIME_3 = LocalDateTime.of(2025, 5, 12, 19, 00);
	private static final LocalDateTime END_TIME_3 = LocalDateTime.of(2025, 5, 12, 21, 10);
	private static final LocalDateTime START_TIME_WRONG = LocalDateTime.of(2025, 1, 12, 19, 00);

	private ShowtimeDto showtimeDto = new ShowtimeDto(TITLE_1, YEAR_1, THEATER_1, 100, START_TIME_1);
	private ResponseShowtimeDto responseShowtimeDto = new ResponseShowtimeDto(TITLE_1, THEATER_1, 100, START_TIME_1, END_TIME_1);
	private ShowtimeDto updatedShowtimeDto = new ShowtimeDto(TITLE_1, YEAR_1, THEATER_1, 110, START_TIME_2);
	private ResponseShowtimeDto updatedResponseShowtimeDto = new ResponseShowtimeDto(TITLE_1, THEATER_1, 110, START_TIME_2, END_TIME_2);
	private ShowtimeDto showtimeDtoMissingFields = new ShowtimeDto(TITLE_1, YEAR_1, "", 100, null);
	private ShowtimeDto showtimeDtoWrongFields = new ShowtimeDto(TITLE_1, YEAR_1, THEATER_1, -40, START_TIME_WRONG);

	private List<ResponseShowtimeDto> showtimes = Arrays.asList(
			new ResponseShowtimeDto(TITLE_1, THEATER_1, 100, START_TIME_1, END_TIME_1),
			new ResponseShowtimeDto(TITLE_1, THEATER_2, 110, START_TIME_2, END_TIME_2)
			);

	private List<ResponseShowtimeDto> theaters = Arrays.asList(
			new ResponseShowtimeDto(TITLE_1, THEATER_1, 100, START_TIME_1, END_TIME_1),
			new ResponseShowtimeDto(TITLE_2, THEATER_1, 100, START_TIME_3, END_TIME_3)
			);
	
	String[] errorMessagesShowtimeMissingFields = { ValidationConstants.MISSING_THEATER_TITLE,
			ValidationConstants.MISSING_START_TIME
			};
	
	String[] errorMessagesShowtimeWrongFields = { ValidationConstants.WRONG_START_TIME, 
			ValidationConstants.WRONG_NUMBER_MAX_SEATS
			};

	@Test
	void addShowtime_ReturnsCreatedShowtime() throws Exception {
		when(service.addShowtime(showtimeDto)).thenReturn(responseShowtimeDto);

		String showtimeJSON = mapper.writeValueAsString(showtimeDto);
		
		String response = mockMvc.perform(post(URL_SHOWTIME).contentType(MediaType.APPLICATION_JSON).content(showtimeJSON))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		
		String showtimeResponseJSON = mapper.writeValueAsString(responseShowtimeDto);
		
		assertEquals(showtimeResponseJSON, response);
	}
	
	@Test
	void updateShowtime_ReturnsUpdatedShowtime() throws Exception {
		when(service.updateShowtimeDetails(updatedShowtimeDto)).thenReturn(updatedResponseShowtimeDto);

	    String showtimeJSON = mapper.writeValueAsString(updatedShowtimeDto);

	    String response = mockMvc.perform(put(URL_SHOWTIME)
	                    .contentType(MediaType.APPLICATION_JSON)
	                    .content(showtimeJSON))
	            .andExpect(status().isOk())
	            .andReturn().getResponse().getContentAsString();
	    
	    String showtimeResponseJSON = mapper.writeValueAsString(updatedResponseShowtimeDto);

		assertEquals(showtimeResponseJSON, response);
	}
	
	@Test
	void deleteShowtime_ReturnsDeletedMovie() throws Exception {
		when(service.deleteShowtime(showtimeDto)).thenReturn(responseShowtimeDto);
		
		String showtimeJSON = mapper.writeValueAsString(showtimeDto);

	    String response = mockMvc.perform(delete(URL_SHOWTIME).contentType(MediaType.APPLICATION_JSON)
                .content(showtimeJSON))
	            .andExpect(status().isOk())
	            .andReturn().getResponse().getContentAsString();
	    
	    String showtimeResponseJSON = mapper.writeValueAsString(responseShowtimeDto);

	    assertEquals(showtimeResponseJSON, response);
	}
	@Test
	void getShowtimesByMovie_ReturnsShowtimesList() throws Exception {
		when(service.getShowtimesByMovie(TITLE_1, YEAR_1)).thenReturn(showtimes);
	    
	    String response = mockMvc.perform(get(URL_SHOWTIMES_BY_MOVIE + SHOWTIME_DATA))
	            .andExpect(status().isOk())
	            .andReturn().getResponse().getContentAsString();

	    assertEquals(mapper.writeValueAsString(showtimes), response);
	}

	
	@Test
	void getShowtimesByTheater_ReturnsShowtimesList() throws Exception {
		when(service.getShowtimesByTheater(THEATER_1)).thenReturn(theaters);

	    String response = mockMvc.perform(get(URL_SHOWTIMES_BY_THEATER + THEATER_1))
	            .andExpect(status().isOk())
	            .andReturn().getResponse().getContentAsString();

	    assertEquals(mapper.writeValueAsString(theaters), response);
	}
	
	
	@Test
	void addShowtime_MissingFields_ReturnsAlert() throws Exception {
		String showtimeJSON = mapper.writeValueAsString(showtimeDtoMissingFields);
		String response = mockMvc
				.perform(post(URL_SHOWTIME).contentType(MediaType.APPLICATION_JSON).content(showtimeJSON))
				.andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();
		assertErrorMessages(errorMessagesShowtimeMissingFields, response);
	}

	private void assertErrorMessages(String[] expectedMessages, String response) {
		Arrays.sort(expectedMessages);
		String [] actualMessages = response.split(";");
		Arrays.sort(actualMessages);
		assertArrayEquals(expectedMessages, actualMessages);
		
	}

	@Test
	void addShowtime_WrongFields_ReturnsAlert() throws Exception {
		String showtimeJSON = mapper.writeValueAsString(showtimeDtoWrongFields);
		String response = mockMvc
				.perform(post(URL_SHOWTIME).contentType(MediaType.APPLICATION_JSON).content(showtimeJSON))
				.andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();
		assertErrorMessages(errorMessagesShowtimeWrongFields, response);
	}
	
	@Test
	void addShowtime_ShowtimeAlreadyExists_ReturnsAlert() throws Exception {
		when(service.addShowtime(showtimeDto)).thenThrow(new IllegalStateException(SHOWTIME_ALREADY_EXISTS));
		
		String showtimeJSON = mapper.writeValueAsString(showtimeDto);
		String response = mockMvc.perform(post(URL_SHOWTIME).contentType(MediaType.APPLICATION_JSON).content(showtimeJSON))
				.andExpect(status().isBadRequest())
				.andReturn().getResponse().getContentAsString();
		
		assertEquals(SHOWTIME_ALREADY_EXISTS, response);
	}
	
	@Test
	void deleteShowtime_ShowtimeNotFound_ReturnsAlert() throws Exception {
		when(service.deleteShowtime(showtimeDto)).thenThrow(new ShowtimeNotFoundException());

		String showtimeJSON = mapper.writeValueAsString(showtimeDto);
		String actualJSON = mockMvc.perform(delete(URL_SHOWTIME).contentType(MediaType.APPLICATION_JSON).content(showtimeJSON))
				.andExpect(status().isNotFound()).andReturn().getResponse().getContentAsString();
		
		assertEquals(SHOWTIME_NOT_FOUND, actualJSON);
	}


}
