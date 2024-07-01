package com.coworking_service.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Time;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Тесты для класса {@link SlotServiceImpl}.
 */
@DisplayName("Тесты для SlotServiceImpl")
public class SlotServiceImplTest {

    private SlotServiceImpl slotService;

    @BeforeEach
    void setUp() {
        slotService = new SlotServiceImpl();
    }

    @Test
    @DisplayName("Вычисление времени начала бронирования")
    void testCalculateBookingTimeFrom() {
        int slotNumber = 3;
        LocalTime expectedTime = LocalTime.of(10, 0);

        Time resultTime = slotService.calculateBookingTimeFrom(slotNumber);

        assertEquals(Time.valueOf(expectedTime), resultTime);
    }

    @Test
    @DisplayName("Вычисление времени окончания бронирования")
    void testCalculateBookingTimeTo() {
        int slotNumber = 5;
        int numberOfSlots = 3;
        LocalTime expectedTime = LocalTime.of(15, 0);

        Time resultTime = slotService.calculateBookingTimeTo(slotNumber, numberOfSlots);

        assertEquals(Time.valueOf(expectedTime), resultTime);
    }

    @Test
    @DisplayName("Вычисление времени окончания бронирования, превышение конечного времени")
    void testCalculateBookingTimeTo_ExceedsEndTime() {
        int slotNumber = 10;
        int numberOfSlots = 5;
        LocalTime expectedTime = LocalTime.of(20, 0);

        Time resultTime = slotService.calculateBookingTimeTo(slotNumber, numberOfSlots);

        assertEquals(Time.valueOf(expectedTime), resultTime);
    }

}
