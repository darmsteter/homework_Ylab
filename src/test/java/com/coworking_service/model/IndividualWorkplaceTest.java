package com.coworking_service.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Класс тестов для IndividualWorkplace.
 */
public class IndividualWorkplaceTest {

    private IndividualWorkplace individualWorkplace;

    /**
     * Настройка перед каждым тестом.
     */
    @BeforeEach
    public void setUp() {
        individualWorkplace = new IndividualWorkplace(1);
    }

    /**
     * Тест для проверки конструктора и метода getWorkplaceID.
     */
    @Test
    public void testConstructorAndGetWorkplaceID() {
        assertNotNull(individualWorkplace);
        assertEquals(1, individualWorkplace.getWorkplaceID());
    }
}
