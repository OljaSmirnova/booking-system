package com.att.interview.ticketbookingsystem.model;

import com.att.interview.ticketbookingsystem.dto.BookingResponseDto;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "bookings", uniqueConstraints = @UniqueConstraint(columnNames = {"showtime_id", "seatNumber"}))
@NoArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "showtime_id")
    Showtime showtime;

    @Column(name = "seat_number" ,nullable = false)
    String seatNumber;

    @Column(nullable = false)
    double price;
    
    public BookingResponseDto toResponseDTO() {
        return new BookingResponseDto(
            this.getUser().getName(),
            this.getShowtime().getMovie().getTitleYear().getTitle(),
            this.getShowtime().getTheater(),
            this.getShowtime().getStartTime(),
            this.getSeatNumber(),
            this.getPrice()
        );
    }

}
