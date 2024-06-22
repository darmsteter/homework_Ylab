package com.coworking_service.model.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для перечисления {@link Commands}.
 */
class CommandsTest {
    /**
     * Проверяет, что перечисление {@link Commands} содержит ожидаемое количество значений.
     */
    @Test
    public void testCommandEnumValues() {
        Commands[] values = Commands.values();
        assertEquals(3, values.length);
        assertEquals("R", values[0].getCommand());
        assertEquals("L", values[1].getCommand());
        assertEquals("S", values[2].getCommand());
    }

    /**
     * Проверяет метод {@link Commands#getCommand()} для команды {@link Commands#REGISTRATION}.
     */
    @Test
    public void testGetCommandForRegistration() {
        assertEquals("R", Commands.REGISTRATION.getCommand());
    }

    /**
     * Проверяет метод {@link Commands#getCommand()} для команды {@link Commands#LOG_IN}.
     */
    @Test
    public void testGetCommandForLogIn() {
        assertEquals("L", Commands.LOG_IN.getCommand());
    }

    /**
     * Проверяет метод {@link Commands#getCommand()} для команды {@link Commands#START_PAGE}.
     */
    @Test
    public void testGetCommandForStartPage() {
        assertEquals("S", Commands.START_PAGE.getCommand());
    }
}