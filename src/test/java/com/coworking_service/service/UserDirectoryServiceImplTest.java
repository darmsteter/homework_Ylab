package com.coworking_service.service;

import com.coworking_service.exception.NoSuchUserExistsException;
import com.coworking_service.model.User;
import com.coworking_service.repository.UserDirectory;
import com.coworking_service.model.enums.Role;
import com.coworking_service.service.interfaces.UserDirectoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для класса UserDirectoryServiceImpl.
 */
class UserDirectoryServiceImplTest {
    private UserDirectoryService userDirectoryService;

    /**
     * Подготавливает тестовое окружение перед каждым тестом.
     */
    @BeforeEach
    public void setUp() {
        User initialUser = new User("admin", "admin", Role.ADMINISTRATOR);
        UserDirectory userDirectory = new UserDirectory(initialUser);
        userDirectoryService = new UserDirectoryServiceImpl(userDirectory);
    }

    /**
     * Тест для метода findUserByLogin(), проверяющий поиск пользователя по логину.
     */
    @Test
    public void testFindUserByLogin() {
        try {
            User foundUser = userDirectoryService.findUserByLogin("admin");
            assertEquals("admin", foundUser.login());
            assertEquals("admin", foundUser.password());
            assertEquals(Role.ADMINISTRATOR, foundUser.role());
        } catch (NoSuchUserExistsException e) {
            fail("User 'admin' should exist in the directory.");
        }
    }

    /**
     * Тест для метода checkIsUserExist(), проверяющий наличие пользователя по логину.
     */
    @Test
    public void testCheckIsUserExist() {
        assertTrue(userDirectoryService.checkIsUserExist("admin"));
        assertFalse(userDirectoryService.checkIsUserExist("user"));
    }
}