package com.att.interview.ticketbookingsystem.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.att.interview.ticketbookingsystem.dto.UserDto;
import com.att.interview.ticketbookingsystem.exception.*;
import com.att.interview.ticketbookingsystem.model.User;
import com.att.interview.ticketbookingsystem.repo.UsersRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsersServiceImpl implements UsersService {
	final UsersRepo usersRepo;
	final PasswordEncoder passwordEncoder;

	@Override
	public UserDto addUser(UserDto userDto) {
        String email = userDto.email();
        User user = null;
        UserDto encodedUser = getEncoded(userDto);
        try {
            user = usersRepo.save(User.fromDto(encodedUser));
        } catch (DataIntegrityViolationException e) {
            throw new UserStateExeption(email);
        }
        log.debug("User {} has been saved", email);
        return user.toDto();
	}

	private UserDto getEncoded(UserDto userDto) {		
		return new UserDto(
				userDto.name(),
				userDto.email(),
				passwordEncoder.encode(userDto.password()),
				userDto.role());
	}

	@Override
	public UserDto removeUser(String email) {
        User user = usersRepo.findById(email).orElseThrow(() -> new UserNotFoundException());
        usersRepo.deleteById(email);
        log.debug("User {} has been removed", email);
        return user.toDto();
	}

	@Override
	public void updatePassword(String email, String newPassword) {
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!currentUser.equals(email)) {
            throw new IllegalArgumentException("Username mismatching");
        }
        User user = usersRepo.findById(email).orElseThrow(() -> new UserNotFoundException());
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        usersRepo.save(user);
        log.debug("Password for user {} has been updated", email);

	}

	@Override
	public void updateUserName(String email, String newUserName) {
		String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!currentUser.equals(email)) {
            throw new IllegalArgumentException("Username mismatching");
        }
        User user = usersRepo.findById(email).orElseThrow(() -> new UserNotFoundException());
        user.setName(newUserName);
        usersRepo.save(user);
        log.debug("Username for user {} has been updated to {}", email, newUserName);
	}

}
