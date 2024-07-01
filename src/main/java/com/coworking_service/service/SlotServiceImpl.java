package com.coworking_service.service;

import com.coworking_service.service.interfaces.SlotService;

import java.sql.Time;
import java.time.LocalTime;

/**
 * Реализация интерфейса SlotService.
 */
public class SlotServiceImpl implements SlotService {
    private static final LocalTime START_TIME = LocalTime.of(8, 0);
    private static final LocalTime END_TIME = LocalTime.of(20, 0);

    /**
     * Вычисляет время начала бронирования на основе номера слота.
     *
     * @param slotNumber номер первого бронируемого слота
     * @return время начала бронирования
     */
    public Time calculateBookingTimeFrom(int slotNumber) {
        LocalTime startTime = START_TIME.plusHours(slotNumber - 1);

        return Time.valueOf(startTime);
    }

    /**
     * Вычисляет время окончания бронирования на основе номера слота и количества слотов.
     *
     * @param slotNumber    номер первого бронируемого слота
     * @param numberOfSlots количество бронируемых слотов
     * @return время окончания бронирования
     */
    public Time calculateBookingTimeTo(int slotNumber, int numberOfSlots) {
        LocalTime startTime = START_TIME.plusHours(slotNumber - 1);
        LocalTime endTime = startTime.plusHours(numberOfSlots);

        if (endTime.isAfter(END_TIME)) {
            endTime = END_TIME;
        }

        return Time.valueOf(endTime);
    }
}
