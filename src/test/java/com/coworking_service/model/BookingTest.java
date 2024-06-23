package com.coworking_service.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для класса Booking.
 */
public class BookingTest {

    /**
     * Тест для проверки создания объекта Booking и его геттеров.
     */
    @Test
    public void testBookingConstructorAndGetters() {
        String userLogin = "testUser";
        int workplaceID = 1;
        String workplaceType = "Individual";
        LocalDate bookingDate = LocalDate.of(2024, 6, 24);
        String[] slots = {"10:00", "11:00"};

        Booking booking = new Booking(userLogin, workplaceID, workplaceType, bookingDate, slots);

        assertEquals(userLogin, booking.userLogin());
        assertEquals(workplaceID, booking.workplaceID());
        assertEquals(workplaceType, booking.workplaceType());
        assertEquals(bookingDate, booking.bookingDate());
        assertArrayEquals(slots, booking.slots());
    }
}
