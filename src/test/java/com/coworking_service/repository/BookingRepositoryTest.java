package com.coworking_service.repository;

import com.coworking_service.entity.Booking;
import com.coworking_service.exception.PersistException;
import com.coworking_service.exception.WrongDataException;
import org.junit.jupiter.api.*;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookingRepositoryTest {

    private BookingRepository bookingRepository;

    @BeforeAll
    public void setUp() {
        bookingRepository = new BookingRepository();
    }

    @AfterAll
    public void tearDown() {
    }

    @Test
    @DisplayName("Создание бронирования")
    public void testCreateBooking() throws PersistException, SQLException, WrongDataException {
        Booking booking = new Booking(1, 1, 2, Date.valueOf("2024-07-01"), Time.valueOf("09:00:00"), Time.valueOf("17:00:00"));
        bookingRepository.create(booking);

        assertNotNull(booking.getPK());
    }

    @Test
    @DisplayName("Получение брони по дате")
    public void testGetBookingsByDateAndUser() throws PersistException {
        Date date = Date.valueOf("2024-07-01");
        Integer userId = 1;
        List<Booking> bookings = bookingRepository.getBookingsByDateAndUser(date, userId);

        assertNotNull(bookings);
        assertFalse(bookings.isEmpty());
        assertTrue(bookings.stream().allMatch(b -> b.getBookingDate().equals(date) && b.getUserId().equals(userId)));
    }
}
