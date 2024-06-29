package com.coworking_service.entity;

import java.sql.Date;
import java.sql.Time;
import java.time.format.DateTimeFormatter;

/**
 * Представляет запись о бронировании рабочего места.
 *
 * @param bookingId       идентификатор бронирования.
 * @param userId          идентификатор пользователя, осуществившего бронирование.
 * @param workplaceId     идентификатор рабочего места.
 * @param bookingDate     дата бронирования.
 * @param bookingTimeFrom время начала бронирования.
 * @param bookingTimeTo   время окончания бронирования.
 */
public record Booking(Integer bookingId,
                      Integer userId,
                      Integer workplaceId,
                      Date bookingDate,
                      Time bookingTimeFrom,
                      Time bookingTimeTo) implements Entity<Integer> {

    /**
     * Возвращает уникальный идентификатор бронирования.
     *
     * @return первичный ключ.
     */
    @Override
    public Integer getPK() {
        return bookingId;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    @Override
    public String toString() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        return String.format("Бронь ID: %d, Место ID: %d, Дата: %s, Время: %s - %s",
                getPK(),
                workplaceId(),
                bookingDate().toLocalDate().format(dateFormatter),
                bookingTimeFrom().toLocalTime().format(timeFormatter),
                bookingTimeTo().toLocalTime().format(timeFormatter)
        );
    }

}

