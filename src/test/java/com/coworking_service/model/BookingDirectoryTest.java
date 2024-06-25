package com.coworking_service.model;

import com.coworking_service.repository.BookingDirectory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для класса BookingDirectory.
 */
public class BookingDirectoryTest {

    private BookingDirectory bookingDirectory;
    private Booking booking1;
    private Booking booking2;
    private Booking booking3;

    /**
     * Подготовка тестового окружения перед каждым тестом.
     */
    @BeforeEach
    public void setUp() {
        bookingDirectory = new BookingDirectory();
        booking1 = new Booking("user1", 1, "Individual", LocalDate.of(2024, 6, 24), new String[]{"10:00", "11:00"});
        booking2 = new Booking("user2", 2, "Conference", LocalDate.of(2024, 6, 25), new String[]{"13:00", "14:00"});
        booking3 = new Booking("user1", 3, "Individual", LocalDate.of(2024, 6, 26), new String[]{"15:00", "16:00"});
        bookingDirectory.addBooking(booking1);
        bookingDirectory.addBooking(booking2);
        bookingDirectory.addBooking(booking3);
    }

    /**
     * Тест для метода addBooking().
     * Проверяет, что бронирование было успешно добавлено в список бронирований.
     */
    @Test
    public void testAddBooking() {
        Booking booking = new Booking("user3", 4, "Individual", LocalDate.of(2024, 6, 27), new String[]{"17:00", "18:00"});
        bookingDirectory.addBooking(booking);
        assertTrue(bookingDirectory.getBookings().contains(booking));
    }

    /**
     * Тест для метода sortByDate().
     * Проверяет, что бронирования отсортированы по дате.
     */
    @Test
    public void testSortByDate() {
        List<Booking> sortedBookings = bookingDirectory.sortByDate();
        assertEquals(booking1, sortedBookings.get(0));
        assertEquals(booking2, sortedBookings.get(1));
        assertEquals(booking3, sortedBookings.get(2));
    }

    /**
     * Тест для метода sortByUser().
     * Проверяет, что бронирования отсортированы по логину пользователя.
     */
    @Test
    public void testSortByUser() {
        List<Booking> sortedBookings = bookingDirectory.sortByUser();
        assertEquals(booking1, sortedBookings.get(0));
        assertEquals(booking3, sortedBookings.get(1));
        assertEquals(booking2, sortedBookings.get(2));
    }

    /**
     * Тест для метода getBookings().
     * Проверяет, что метод возвращает все бронирования.
     */
    @Test
    public void testGetBookings() {
        List<Booking> bookings = bookingDirectory.getBookings();
        assertTrue(bookings.contains(booking1));
        assertTrue(bookings.contains(booking2));
        assertTrue(bookings.contains(booking3));
    }

    /**
     * Тест для метода getBooking().
     * Проверяет, что метод возвращает правильное бронирование по логину пользователя и дате.
     * Также проверяет случай, когда бронирование не найдено.
     */
    @Test
    public void testGetBooking() {
        Booking booking = bookingDirectory.getBooking("user1", LocalDate.of(2024, 6, 24));
        assertEquals(booking1, booking);

        Booking notFoundBooking = bookingDirectory.getBooking("user3", LocalDate.of(2024, 6, 28));
        assertNull(notFoundBooking);
    }

    /**
     * Тест для метода removeBooking().
     * Проверяет, что бронирование было успешно удалено из списка бронирований.
     */
    @Test
    public void testRemoveBooking() {
        bookingDirectory.removeBooking(booking1);
        assertFalse(bookingDirectory.getBookings().contains(booking1));
    }

    /**
     * Тест для метода getBookingsByUser().
     * Проверяет, что метод возвращает все бронирования, принадлежащие указанному пользователю.
     */
    @Test
    public void testGetBookingsByUser() {
        List<Booking> userBookings = bookingDirectory.getBookingsByUser("user1");
        assertTrue(userBookings.contains(booking1));
        assertTrue(userBookings.contains(booking3));
        assertFalse(userBookings.contains(booking2));
    }
}
