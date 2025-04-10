package com.att.interview.ticketbookingsystem.controller;

import static com.att.interview.ticketbookingsystem.api.ValidationConstants.*;
import static com.att.interview.ticketbookingsystem.api.UrlConstants.*;

import org.springframework.web.bind.annotation.*;

import com.att.interview.ticketbookingsystem.dto.PasswordUpdateData;
import com.att.interview.ticketbookingsystem.dto.UserDto;
import com.att.interview.ticketbookingsystem.dto.UserNameUpdateData;
import com.att.interview.ticketbookingsystem.service.UsersService;

import jakarta.validation.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

	final UsersService userService;

    @PostMapping
    UserDto addUser(@RequestBody @Valid UserDto userDto) {
        log.debug("addUser: received user data {}", userDto);
        return userService.addUser(userDto);
    }

    @DeleteMapping("/{email}")
    UserDto removeUser(@PathVariable @NotEmpty(message = MISSING_EMAIL) @Email(message = WRONG_EMAIL_FORMAT) String email) {
        log.debug("removeUser: deleting user with email {}", email);
        return userService.removeUser(email);
    }

    @PutMapping(PASSWORD)
    String updatePassword(@RequestBody @Valid PasswordUpdateData updateData) {
        log.debug("updatePassword: updating password for email {}", updateData.email());
        userService.updatePassword(updateData.email(), updateData.password());
        return "Password has been updated";
    }

    @PutMapping(USER_NAME)
    String updateUserName(@RequestBody @Valid UserNameUpdateData updateData) {
        log.debug("updateUserName: updating username for email {}", updateData.email());
        userService.updateUserName(updateData.email(), updateData.name());
        return "User name has been updated";
    }
}
