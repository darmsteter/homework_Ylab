package com.coworking_service.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Тесты для класса ConferenceRoom.
 */
public class ConferenceRoomTest {

    /**
     * Тест для конструктора ConferenceRoom и метода getWorkplaceID().
     * Проверяет, что номер рабочего места установлен правильно.
     */
    @Test
    public void testConstructorAndGetWorkplaceID() {
        int expectedWorkplaceID = 1;
        int expectedMaximumCapacity = 10;
        ConferenceRoom conferenceRoom = new ConferenceRoom(expectedWorkplaceID, expectedMaximumCapacity);

        assertEquals(expectedWorkplaceID, conferenceRoom.getWorkplaceID());
    }

    /**
     * Тест для метода getMaximumCapacity().
     * Проверяет, что максимальная вместимость конференц-зала установлена правильно.
     */
    @Test
    public void testGetMaximumCapacity() {
        int expectedWorkplaceID = 1;
        int expectedMaximumCapacity = 10;
        ConferenceRoom conferenceRoom = new ConferenceRoom(expectedWorkplaceID, expectedMaximumCapacity);

        assertEquals(expectedMaximumCapacity, conferenceRoom.getMaximumCapacity());
    }
}
