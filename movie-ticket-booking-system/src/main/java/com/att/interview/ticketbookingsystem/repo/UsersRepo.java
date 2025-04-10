package com.att.interview.ticketbookingsystem.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.att.interview.ticketbookingsystem.model.User;

public interface UsersRepo extends JpaRepository<User, String> {

}
