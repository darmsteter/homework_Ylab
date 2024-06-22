package com.coworking_service.util;

import com.coworking_service.model.enums.MessageType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для класса ConsoleUtil.
 */
class ConsoleUtilTest {
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    /**
     * Подготавливает тестовое окружение перед каждым тестом.
     */
    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    /**
     * После выполнения каждого теста восстанавливает исходное тестовое окружение.
     */
    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
    }

    /**
     * Тест для метода printMessage(), проверяющий корректность вывода приветственного сообщения на консоль.
     */
    @Test
    public void testPrintWelcomeMessage() {
        ConsoleUtil.printMessage(MessageType.WELCOME);
        assertEquals("Добро пожаловать в управление коворкинг-пространством!\n", outputStreamCaptor.toString());
    }

    /**
     * Тест для метода printMessage(), проверяющий вывод сообщения об ошибке неправильного пароля на консоль.
     */
    @Test
    public void testPrintIncorrectPasswordMessage() {
        ConsoleUtil.printMessage(MessageType.INCORRECT_PASSWORD_ERROR);
        assertNotEquals("Пользователь не найден, введите корректный логин:\n", outputStreamCaptor.toString());
    }
}