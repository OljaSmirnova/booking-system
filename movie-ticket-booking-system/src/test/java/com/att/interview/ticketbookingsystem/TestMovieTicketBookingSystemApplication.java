package com.att.interview.ticketbookingsystem;

import org.springframework.boot.SpringApplication;

public class TestMovieTicketBookingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.from(MovieTicketBookingSystemApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
