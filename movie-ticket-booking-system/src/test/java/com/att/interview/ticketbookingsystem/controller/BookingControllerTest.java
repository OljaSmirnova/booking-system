package com.att.interview.ticketbookingsystem.controller;

import static com.att.interview.ticketbookingsystem.api.ServiceExceptionMessages.*;
import static com.att.interview.ticketbookingsystem.api.UrlConstants.*;
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
import com.att.interview.ticketbookingsystem.service.BookingService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(BookingController.class)
@Import({TestSecurityConfig.class, ExceptionController.class})
class BookingControllerTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper mapper;
	
	@MockitoBean
	BookingService service;
	
	private static final String HOST = "http://localhost:8000/";
	private static final String URL_BOOKINGS = HOST + BOOKINGS;
	private static final String URL_GET_BOOKINGS = URL_BOOKINGS + "/user@at&t.co.il";
	
	private static final String MOVIE_TITLE_1 = "Inception";
	private static final int PRICE_1 = 2010;
	private static final String THEATER_1 = "A-20";
	private static final String USER_ID_1 = "user@at&t.co.il";
	private static final String USER_NAME_1 = "Ron";
	private static final LocalDateTime MOVIE_SESSION_1 = LocalDateTime.of(2025, 5, 12, 15, 40);
	private static final String SEAT_NUMBER = "A-13";
	private static final String SEAT_NUMBER_2 = "A-14";

	private BookingDto bookingDto = new BookingDto(USER_ID_1, MOVIE_TITLE_1, THEATER_1, MOVIE_SESSION_1, SEAT_NUMBER, PRICE_1);
	private BookingResponseDto bookingResponseDto = new BookingResponseDto(USER_NAME_1, MOVIE_TITLE_1, THEATER_1, MOVIE_SESSION_1, SEAT_NUMBER, PRICE_1);
	private BookingDto bookingDtoMissingFields = new BookingDto(USER_ID_1, MOVIE_TITLE_1, THEATER_1, MOVIE_SESSION_1, "", 0);
	private BookingDto bookingDtoWrongFields = new BookingDto(USER_ID_1, MOVIE_TITLE_1, THEATER_1, MOVIE_SESSION_1, "4-4", -135);

	private List<BookingResponseDto> bookings = Arrays.asList(
			new BookingResponseDto(USER_NAME_1, MOVIE_TITLE_1, THEATER_1, MOVIE_SESSION_1, SEAT_NUMBER, PRICE_1),
			new BookingResponseDto(USER_NAME_1, MOVIE_TITLE_1, THEATER_1, MOVIE_SESSION_1, SEAT_NUMBER_2, PRICE_1)		
			);
	
	String[] errorMessagesShowtimeMissingFields = { ValidationConstants.MISSING_SEAT_NUMBER,
			ValidationConstants.WRONG_SEAT_NUMBER
			};
	
	String[] errorMessagesShowtimeWrongFields = { ValidationConstants.WRONG_SEAT_NUMBER, 
			ValidationConstants.WRONG_TICKET_PRICE_VALUE
			};


	@Test
	void bookTicket_ReturnsCreatedTicket() throws Exception {
		when(service.bookTicket(bookingDto)).thenReturn(bookingResponseDto);

		String bookingJSON = mapper.writeValueAsString(bookingDto);
		
		String response = mockMvc.perform(post(URL_BOOKINGS).contentType(MediaType.APPLICATION_JSON).content(bookingJSON))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		
		String bookingResponseJSON = mapper.writeValueAsString(bookingResponseDto);
		
		assertEquals(bookingResponseJSON, response);
	}
	
	@Test
	void getBookingsByUser_ReturnsBookingsList() throws Exception {
		when(service.getBookingsByUser(USER_ID_1)).thenReturn(bookings);
	    
	    String response = mockMvc.perform(get(URL_GET_BOOKINGS))
	            .andExpect(status().isOk())
	            .andReturn().getResponse().getContentAsString();

	    assertEquals(mapper.writeValueAsString(bookings), response);
	}
	
	@Test
	void addShowtime_MissingFields_ReturnsAlert() throws Exception {
		String bookingJSON = mapper.writeValueAsString(bookingDtoMissingFields);
		String response = mockMvc
				.perform(post(URL_BOOKINGS).contentType(MediaType.APPLICATION_JSON).content(bookingJSON))
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
		String bookingJSON = mapper.writeValueAsString(bookingDtoWrongFields);
		String response = mockMvc
				.perform(post(URL_BOOKINGS).contentType(MediaType.APPLICATION_JSON).content(bookingJSON))
				.andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();
		assertErrorMessages(errorMessagesShowtimeWrongFields, response);
	}
	
	@Test
	void bookTicket_SeatAlreadyBooked_ReturnsAlert() throws Exception {
		when(service.bookTicket(bookingDto)).thenThrow(new IllegalStateException(SEAT_ALREADY_BOOKED));
		
		String bookingJSON = mapper.writeValueAsString(bookingDto);
		String response = mockMvc.perform(post(URL_BOOKINGS).contentType(MediaType.APPLICATION_JSON).content(bookingJSON))
				.andExpect(status().isBadRequest())
				.andReturn().getResponse().getContentAsString();
		
		assertEquals(SEAT_ALREADY_BOOKED, response);
	}
	
}
