/*
package com.coworking_service.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

*/
/**
 * Тесты для класса CoworkingSpace.
 *//*

public class CoworkingSpaceTest {

    private CoworkingSpace coworkingSpace;

    @BeforeEach
    public void setUp() {
        coworkingSpace = new CoworkingSpace();
    }

    */
/**
     * Тест для метода addIndividualWorkplace().
     * Проверяет добавление нового индивидуального рабочего места.
     *//*

    @Test
    public void testAddIndividualWorkplace() {
        coworkingSpace.addIndividualWorkplace();
        HashMap<IndividualWorkplace, Map<String, Slot>> individualWorkplaces = coworkingSpace.getIndividualWorkplaces();
        assertEquals(1, individualWorkplaces.size());
    }

    */
/**
     * Тест для метода addConferenceRoom().
     * Проверяет добавление нового конференц-зала.
     *//*

    @Test
    public void testAddConferenceRoom() {
        coworkingSpace.addConferenceRoom(10);
        HashMap<ConferenceRoom, Map<String, Slot>> conferenceRooms = coworkingSpace.getConferenceRooms();
        assertEquals(1, conferenceRooms.size());
    }

    */
/**
     * Тест для метода getIndividualWorkplacesSlotsByDate().
     * Проверяет получение слотов для индивидуальных рабочих мест на указанную дату.
     *//*

    @Test
    public void testGetIndividualWorkplacesSlotsByDate() {
        coworkingSpace.addIndividualWorkplace();
        coworkingSpace.getIndividualWorkplacesSlotsByDate(LocalDate.of(2024, 6, 24));
    }

    */
/**
     * Тест для метода getConferenceRoomsSlotsByDate().
     * Проверяет получение слотов для конференц-залов на указанную дату.
     *//*

    @Test
    public void testGetConferenceRoomsSlotsByDate() {
        coworkingSpace.addConferenceRoom(10);
        coworkingSpace.getConferenceRoomsSlotsByDate(LocalDate.of(2024, 6, 24));
    }

    */
/**
     * Тест для метода findIndividualWorkplaceById().
     * Проверяет поиск индивидуального рабочего места по его ID.
     *//*

    @Test
    public void testFindIndividualWorkplaceById() {
        coworkingSpace.addIndividualWorkplace();
        IndividualWorkplace workplace = coworkingSpace.findIndividualWorkplaceById(0);
        assertNotNull(workplace);
        assertEquals(0, workplace.getWorkplaceID());
    }

    */
/**
     * Тест для метода findConferenceRoomById().
     * Проверяет поиск конференц-зала по его ID.
     *//*

    @Test
    public void testFindConferenceRoomById() {
        coworkingSpace.addConferenceRoom(10);
        ConferenceRoom conferenceRoom = coworkingSpace.findConferenceRoomById(0);
        assertNotNull(conferenceRoom);
        assertEquals(0, conferenceRoom.getWorkplaceID());
        assertEquals(10, conferenceRoom.getMaximumCapacity());
    }

    */
/**
     * Тест для метода reserveSlots().
     * Проверяет резервирование слотов для рабочего места на указанную дату.
     *//*

    @Test
    public void testReserveSlots() {
        coworkingSpace.addIndividualWorkplace();
        IndividualWorkplace workplace = coworkingSpace.findIndividualWorkplaceById(0);
        Map<String, Slot> slotsMap = coworkingSpace.getIndividualWorkplaces().get(workplace);

        LocalDate date = LocalDate.of(2024, 6, 24);
        Slot slot = new Slot(date);
        slotsMap.put(date.toString(), slot);

        String[] reservedSlots = coworkingSpace.reserveSlots(workplace, slotsMap, 0, date, 1, 2);

        assertNotNull(reservedSlots);
        assertEquals(2, reservedSlots.length);
        assertFalse(slot.isSlotAvailable(0));
        assertFalse(slot.isSlotAvailable(1));
    }

    */
/**
     * Тест для метода setSlotsAvailability().
     * Проверяет установление доступности слотов для рабочего места.
     *//*

    @Test
    public void testSetSlotsAvailability() {
        coworkingSpace.addIndividualWorkplace();
        IndividualWorkplace workplace = coworkingSpace.findIndividualWorkplaceById(0);
        Map<String, Slot> slotsMap = coworkingSpace.getIndividualWorkplaces().get(workplace);

        LocalDate date = LocalDate.of(2024, 6, 24);
        Slot slot = new Slot(date);
        slotsMap.put(date.toString(), slot);

        String[] slotsToReserve = new String[]{slot.getSlots()[0].key(), slot.getSlots()[1].key()};
        coworkingSpace.setSlotsAvailability(workplace, slotsToReserve, false);

        assertFalse(slot.isSlotAvailable(0));
        assertFalse(slot.isSlotAvailable(1));
    }
}
*/
