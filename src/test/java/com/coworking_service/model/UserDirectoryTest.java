package com.coworking_service.model;

import com.coworking_service.exception.NoSuchUserExistsException;
import com.coworking_service.model.enums.Role;
import com.coworking_service.repository.UserDirectory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для класса UserDirectory.
 */
class UserDirectoryTest {
    private UserDirectory userDirectory;

    /**
     * Подготовка тестового окружения перед каждым тестом.
     */
    @BeforeEach
    public void setUp() {
        User initialUser = new User("admin", "admin", Role.ADMINISTRATOR);
        userDirectory = new UserDirectory(initialUser);
    }

    /**
     * Тест для метода isLoginExist(), проверяющий наличие пользователя по логину.
     */
    @Test
    public void testIsLoginExist() {
        assertTrue(userDirectory.isLoginExist("admin"));
        assertFalse(userDirectory.isLoginExist("user"));
    }

    /**
     * Тест для метода findByLogin(), проверяющий поиск пользователя по логину.
     */
    @Test
    public void testFindByLogin() {
        try {
            User foundUser = userDirectory.findByLogin("admin");
            assertEquals("admin", foundUser.login());
            assertEquals("admin", foundUser.password());
            assertEquals(Role.ADMINISTRATOR, foundUser.role());
        } catch (NoSuchUserExistsException e) {
            fail("User 'admin' should exist in the directory.");
        }

        assertThrows(NoSuchUserExistsException.class, () -> userDirectory.findByLogin("unknownUser"));
    }

    /**
     * Тест для метода addNewUser(), проверяющий добавление нового пользователя в директорию.
     */
    @Test
    public void testAddNewUser() {
        User newUser = new User("user", "password", Role.USER);
        userDirectory.addNewUser("user", newUser);

        assertTrue(userDirectory.isLoginExist("user"));

        try {
            User addedUser = userDirectory.findByLogin("user");
            assertEquals("user", addedUser.login());
            assertEquals("password", addedUser.password());
            assertEquals(Role.USER, addedUser.role());
        } catch (NoSuchUserExistsException e) {
            fail("User 'user' should have been added successfully.");
        }
    }
}