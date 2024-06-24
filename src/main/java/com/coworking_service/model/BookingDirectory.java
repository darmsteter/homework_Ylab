package com.coworking_service.model;

import java.util.*;
import java.util.stream.Collectors;

import java.time.LocalDate;

/**
 * Класс, представляющий директорию бронирований, хранящий список всех сделанных броней.
 */
public class BookingDirectory {
    private final Map<LocalDate, Map<String, Booking>> bookings;

    /**
     * Конструктор для инициализации нового экземпляра реестра бронирований.
     */
    public BookingDirectory() {
        this.bookings = new HashMap<>();
    }

    /**
     * Добавляет новое бронирование в реестр.
     *
     * @param booking объект, который необходимо добавить
     */
    public void addBooking(Booking booking) {
        bookings
                .computeIfAbsent(booking.bookingDate(), k -> new HashMap<>())
                .put(booking.userLogin(), booking);
    }

    /**
     * Возвращает список всех бронирований, отсортированный по дате бронирования.
     *
     * @return список бронирований, отсортированный по дате
     */
    public List<Booking> sortByDate() {
        return bookings.values().stream()
                .flatMap(map -> map.values().stream())
                .sorted(Comparator.comparing(Booking::bookingDate))
                .collect(Collectors.toList());
    }

    /**
     * Возвращает список всех бронирований, отсортированный по логину пользователя.
     *
     * @return список бронирований, отсортированный по логину пользователя
     */
    public List<Booking> sortByUser() {
        return bookings.values().stream()
                .flatMap(map -> map.values().stream())
                .sorted(Comparator.comparing(Booking::userLogin))
                .collect(Collectors.toList());
    }

    /**
     * Возвращает список всех бронирований в реестре.
     *
     * @return список всех бронирований
     */
    public List<Booking> getBookings() {
        return bookings.values().stream()
                .flatMap(map -> map.values().stream())
                .collect(Collectors.toList());
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
        Map<String, Booking> dateBookings = bookings.get(date);
        if (dateBookings != null) {
            return dateBookings.get(userLogin);
        }
        return null;
    }

    /**
     * Удаляет бронирование из реестра.
     *
     * @param booking объект, который необходимо удалить
     */
    public void removeBooking(Booking booking) {
        Map<String, Booking> dateBookings = bookings.get(booking.bookingDate());
        if (dateBookings != null) {
            dateBookings.remove(booking.userLogin());
            if (dateBookings.isEmpty()) {
                bookings.remove(booking.bookingDate());
            }
        }
    }

    /**
     * Возвращает список всех бронирований, принадлежащих указанному пользователю.
     *
     * @param userLogin логин пользователя
     * @return список бронирований, принадлежащих указанному пользователю
     */
    public List<Booking> getBookingsByUser(String userLogin) {
        return bookings.values().stream()
                .map(map -> map.get(userLogin))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
