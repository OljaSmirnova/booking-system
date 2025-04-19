package com.att.interview.ticketbookingsystem.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.att.interview.exception.controller.ExceptionController;
import com.att.interview.ticketbookingsystem.api.ServiceExceptionMessages;
import com.att.interview.ticketbookingsystem.api.ValidationConstants;
import com.att.interview.ticketbookingsystem.config.TestSecurityConfig;
import com.att.interview.ticketbookingsystem.dto.*;
import com.att.interview.ticketbookingsystem.exception.*;
import com.att.interview.ticketbookingsystem.service.UsersService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static com.att.interview.ticketbookingsystem.api.UrlConstants.*;


@WebMvcTest(UserController.class)
@Import({TestSecurityConfig.class, ExceptionController.class})
class UserControllerTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper mapper;
	
	@MockitoBean
	UsersService service;
	
	private static final String HOST = "http://localhost:8000/";
	private static final String URL_USERS = HOST + USERS;
	private static final String URL_DELETE_USERS = URL_USERS + "/user@at&t.co.il";
	
	private static final String EMAIL_1 = "user@at&t.co.il";
	private static final String USER_NAME_1 = "Ron";
	private static final String NEW_USER_NAME = "Max";
	private static final String PASSWORD_1 = "dgjhdfghd";
	private static final String NEW_PASSWORD = "sfgaiaf";

	private UserDto userDto = new UserDto(USER_NAME_1, EMAIL_1, PASSWORD_1, Role.CUSTOMER);
	private UserDto userResponseDto = new UserDto(USER_NAME_1, EMAIL_1, "**********", Role.CUSTOMER);
	private UserDto userMissingFieldsDto = new UserDto(null, "", null, Role.CUSTOMER);
	private UserDto userWrongFieldsDto = new UserDto("a", "user@", "a", Role.CUSTOMER);
	private PasswordUpdateData passwordData = new PasswordUpdateData(EMAIL_1, NEW_PASSWORD);
	private UserNameUpdateData nameData = new UserNameUpdateData(EMAIL_1, NEW_USER_NAME);

	private String[] errorMessagesMovieMissingFields = {
			ValidationConstants.MISSING_EMAIL, ValidationConstants.MISSING_USER_NAME, 
			ValidationConstants.MISSING_PASSWORD,
	};

	private String[] errorMessagesMovieWrongFields = {
			ValidationConstants.WRONG_EMAIL_FORMAT, ValidationConstants.WRONG_SIZE_NAME,
			ValidationConstants.WRONG_SIZE_PASSWORD
	};

	@Test
	void addUser_ReturnsCreatedUser() throws Exception {
		when(service.addUser(userDto)).thenReturn(userResponseDto);

		String userJSON = mapper.writeValueAsString(userDto);
		String response = mockMvc.perform(post(URL_USERS).contentType(MediaType.APPLICATION_JSON).content(userJSON))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		
		String userResponseJSON = mapper.writeValueAsString(userResponseDto);
		
		assertEquals(userResponseJSON, response);
	}

	@Test
	void removeUser_ReturnsRemovedUser() throws Exception {
		when(service.removeUser(EMAIL_1)).thenReturn(userDto);
		
	    String userJSON = mapper.writeValueAsString(userDto);
	    String response = mockMvc.perform(delete(URL_DELETE_USERS))
	            .andExpect(status().isOk())
	            .andReturn().getResponse().getContentAsString();

	    assertEquals(userJSON, response);
	}
	
	@Test
	void updatePassword_CheckingOperation() throws Exception {
		String userJSON = mapper.writeValueAsString(passwordData);
		mockMvc.perform(put(URL_USERS + PASSWORD).contentType(MediaType.APPLICATION_JSON)
				.content(userJSON)).andExpect(status().isOk());
		
		verify(service).updatePassword(EMAIL_1, NEW_PASSWORD);
	}
	
	@Test
	void updateUserName_CheckingOperation() throws Exception {
		String userJSON = mapper.writeValueAsString(nameData);
		mockMvc.perform(put(URL_USERS + USER_NAME).contentType(MediaType.APPLICATION_JSON)
				.content(userJSON)).andExpect(status().isOk());
		
		verify(service).updateUserName(EMAIL_1, NEW_USER_NAME);		
	}
	
	@Test
	void addUser_MissingFields_ReturnsAlert() throws Exception {
		String userJSON = mapper.writeValueAsString(userMissingFieldsDto);
		String response = mockMvc
				.perform(post(URL_USERS).contentType(MediaType.APPLICATION_JSON).content(userJSON))
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
	void addUser_WrongFields_ReturnsAlert() throws Exception {
		String userJSON = mapper.writeValueAsString(userWrongFieldsDto);
		String response = mockMvc
				.perform(post(URL_USERS).contentType(MediaType.APPLICATION_JSON).content(userJSON))
				.andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();
		assertErrorMessages(errorMessagesMovieWrongFields, response);
	}
	
	@Test
	void addUser_UserAlreadyExists_ReturnsAlert() throws Exception {
		when(service.addUser(userDto)).thenThrow(new UserStateExeption(EMAIL_1));
		
		String userJSON = mapper.writeValueAsString(userDto);
		String response = mockMvc.perform(post(URL_USERS).contentType(MediaType.APPLICATION_JSON).content(userJSON))
				.andExpect(status().isBadRequest())
				.andReturn().getResponse().getContentAsString();
		
		assertEquals(String.format(ServiceExceptionMessages.USER_ALREADY_EXISTS, EMAIL_1), response);
	}
	
	@Test
	void removeUser_UserNotFound_ReturnsAlert() throws Exception {
		when(service.removeUser(EMAIL_1)).thenThrow(new UserNotFoundException());
		
		String actualJSON = mockMvc.perform(delete(URL_DELETE_USERS))
				.andExpect(status().isNotFound()).andReturn().getResponse().getContentAsString();
		
		assertEquals(ServiceExceptionMessages.USER_NOT_FOUND, actualJSON);
	}


}
