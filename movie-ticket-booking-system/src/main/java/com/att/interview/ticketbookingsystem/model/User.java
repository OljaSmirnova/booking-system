package com.att.interview.ticketbookingsystem.model;

import com.att.interview.ticketbookingsystem.dto.Role;
import com.att.interview.ticketbookingsystem.dto.UserDto;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {
	@Id
	@Column(nullable = false, unique = true)
	String email;
	
	@Column(name = "user_name", nullable = false)
	String name;
	
	@Column(nullable = false)
	String password;
	
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
	Role role;
    
    public UserDto toDto() {
        return new UserDto(name, email, "**********", role);
    }

    public static User fromDto(UserDto dto) {
        return new User(dto.name(), dto.email(), dto.password(), dto.role());
    }
    
    private User(String name, String email, String password, Role role) {
    	this.name = name;
    	this.email = email;
    	this.password = password;
    	this.role = role;
    }


}
