package com.coworking_service.model;

import com.coworking_service.model.enums.Role;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для класса User.
 */
class UserTest {
    /**
     * Проверка конструктора и геттеров.
     */
    @Test
    public void testUserConstructorAndGetters() {
        User user = new User("testUser", "password123", Role.USER);
        assertEquals("testUser", user.login());
        assertEquals("password123", user.password());
        assertEquals(Role.USER, user.role());
    }

    /**
     * Проверка методов equals() и hashCode().
     */
    @Test
    public void testEqualsAndHashCode() {
        User user1 = new User("user1", "password1", Role.USER);
        User user1Duplicate = new User("user1", "password1", Role.USER);
        User user2 = new User("admin", "admin", Role.ADMINISTRATOR);

        assertEquals(user1, user1Duplicate);
        assertEquals(user1.hashCode(), user1Duplicate.hashCode());

        assertNotEquals(user1, user2);
    }
}