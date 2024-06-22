package com.coworking_service.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BookingDirectory {
    private List<Booking> bookings;

    public BookingDirectory() {
        this.bookings = new ArrayList<>();
    }

    public void addBooking(Booking booking) {
        bookings.add(booking);
    }

    public List<Booking> sortByDate() {
        return bookings.stream()
                .sorted(Comparator.comparing(Booking::getBookingDate))
                .collect(Collectors.toList());
    }

    public List<Booking> sortByUser() {
        return bookings.stream()
                .sorted(Comparator.comparing(Booking::getUserLogin))
                .collect(Collectors.toList());
    }

    public List<Booking> getBookings() {
        return bookings;
    }
}
