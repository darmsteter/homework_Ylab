package com.coworking_service.model;

import com.coworking_service.util.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class SlotTest {

    /**
     * Тесты для класса Slot.
     */
    private LocalDate testDate;

    /**
     * Подготовка тестового окружения перед каждым тестом.
     */
    @BeforeEach
    public void setUp() {
        testDate = LocalDate.of(2024, 12, 12);
    }

    /**
     * Тест для конструктора Slot и метода getDate().
     */
    @Test
    public void testConstructorAndGetDate() {
        Slot slot = new Slot(testDate);

        assertEquals(testDate, slot.getDate());
    }

    /**
     * Тест для метода generateSlots().
     * Проверяет корректность генерации слотов времени.
     */
    @Test
    public void testGenerateSlots() {
        Slot slot = new Slot(testDate);
        Pair<String, Boolean>[] slots = slot.getSlots();

        assertNotNull(slots);
        assertTrue(slots.length > 0);

        Pair<String, Boolean> firstSlot = slots[0];
        assertNotNull(firstSlot);
        assertTrue(firstSlot.key().contains("слот"));
    }

    /**
     * Тест для метода isSlotAvailable().
     * Проверяет, что слот по указанному индексу доступен.
     */
    @Test
    public void testIsSlotAvailable() {
        Slot slot = new Slot(testDate);
        Pair<String, Boolean>[] slots = slot.getSlots();

        assertTrue(slot.isSlotAvailable(0));
    }

    /**
     * Тест для метода setSlotAvailability().
     * Проверяет установку доступности слота по указанному индексу.
     */
    @Test
    public void testSetSlotAvailability() {
        Slot slot = new Slot(testDate);

        slot.setSlotAvailability(0, false);
        assertFalse(slot.isSlotAvailable(0));
    }
}
