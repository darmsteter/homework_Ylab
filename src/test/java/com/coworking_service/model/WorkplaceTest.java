package com.coworking_service.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Тестовый класс для проверки функциональности класса Workplace.
 */
public class WorkplaceTest {
    /**
     * Тест метода getWorkplaceID() класса Workplace.
     */
    @Test
    public void testGetWorkplaceID() {
        int expectedWorkplaceID = 1;
        Workplace workplace = new MockWorkplace(expectedWorkplaceID);

        int actualWorkplaceID = workplace.getWorkplaceID();

        assertEquals(expectedWorkplaceID, actualWorkplaceID);
    }

    /**
     * Конструктор для создания объекта MockWorkplace с заданным идентификатором рабочего места.
     * param workplaceID идентификатор рабочего места
     */
    private static class MockWorkplace extends Workplace {
        public MockWorkplace(int workplaceID) {
            super(workplaceID);
        }
    }
}
