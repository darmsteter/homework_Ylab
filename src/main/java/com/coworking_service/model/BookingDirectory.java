package com.coworking_service.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import java.time.LocalDate;

/**
 * Класс, представляющий директорию бронирований, хранящий список всех сделанных броней.
 */
public class BookingDirectory {
    private final List<Booking> bookings;

    /**
     * Конструктор для инициализации нового экземпляра реестра бронирований.
     */
    public BookingDirectory() {
        this.bookings = new ArrayList<>();
    }

    /**
     * Добавляет новое бронирование в реестр.
     *
     * @param booking объект, который необходимо добавить
     */
    public void addBooking(Booking booking) {
        bookings.add(booking);
    }

    /**
     * Возвращает список всех бронирований, отсортированный по дате бронирования.
     *
     * @return список бронирований, отсортированный по дате
     */
    public List<Booking> sortByDate() {
        return bookings.stream()
                .sorted(Comparator.comparing(Booking::bookingDate))
                .collect(Collectors.toList());
    }

    /**
     * Возвращает список всех бронирований, отсортированный по логину пользователя.
     *
     * @return список бронирований, отсортированный по логину пользователя
     */
    public List<Booking> sortByUser() {
        return bookings.stream()
                .sorted(Comparator.comparing(Booking::userLogin))
                .collect(Collectors.toList());
    }

    /**
     * Возвращает список всех бронирований в реестре.
     *
     * @return список всех бронирований
     */
    public List<Booking> getBookings() {
        return bookings;
    }

    /**
     * Возвращает бронирование по логину пользователя, дате и начальному слоту.
     *
     * @param userLogin логин пользователя
     * @param date      дата бронирования
     * @return объект бронирования, если найден; иначе null
     */
    public Booking getBooking(String userLogin, LocalDate date) {
        System.out.println("Проверка бронирований для логина: " + userLogin + ", даты: " + date);
        for (Booking booking : bookings) {
            System.out.println("Проверка брони: " + booking);
            if (booking.userLogin().equals(userLogin)
                    && booking.bookingDate().equals(date)) {
                return booking;
            }
        }
        return null;
    }

    /**
     * Удаляет бронирование из реестра.
     *
     * @param booking объект, который необходимо удалить
     */
    public void removeBooking(Booking booking) {
        bookings.remove(booking);
    }

    /**
     * Возвращает список всех бронирований, принадлежащих указанному пользователю.
     *
     * @param userLogin логин пользователя
     * @return список бронирований, принадлежащих указанному пользователю
     */
    public List<Booking> getBookingsByUser(String userLogin) {
        return bookings.stream()
                .filter(booking -> booking.userLogin().equals(userLogin))
                .collect(Collectors.toList());
    }
}
