package com.coworking_service.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
