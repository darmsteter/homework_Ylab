package com.coworking_service.entity;

import java.sql.Date;
import java.sql.Time;

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
}

