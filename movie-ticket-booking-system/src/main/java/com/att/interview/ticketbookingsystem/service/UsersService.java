package com.att.interview.ticketbookingsystem.service;

import com.att.interview.ticketbookingsystem.dto.UserDto;

public interface UsersService {
	UserDto addUser(UserDto userDto);
	UserDto removeUser(String email);
	void updatePassword(String email, String newPassword);
	void updateUserName(String email, String newUserName);
	

}
