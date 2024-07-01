package com.coworking_service.service;

import com.coworking_service.entity.User;
import com.coworking_service.exception.IncorrectPasswordException;
import com.coworking_service.exception.NoSuchUserExistsException;
import com.coworking_service.exception.PersistException;
import com.coworking_service.exception.WrongDataException;
import com.coworking_service.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * Тесты для класса {@link UserServiceImpl}.
 */
@DisplayName("UserServiceImpl Tests")
public class UserServiceImplTest {
    /**
     * Mock-объект UserRepository для целей тестирования.
     */
    @Mock
    private UserRepository mockUserRepository;

    /**
     * Экземпляр UserServiceImpl для проведения тестов.
     */
    private UserServiceImpl userService;

    /**
     * Подготавливает Mockito аннотации и инициализирует UserServiceImpl с mock-объектом UserRepository перед каждым тестом.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl(mockUserRepository);
    }

    @Test
    @DisplayName("Валидация пользователя - пользователь существует и пароль совпадает.")
    void testValidateUser_UserExistsAndPasswordMatches_ReturnsUser()
            throws PersistException, IncorrectPasswordException, NoSuchUserExistsException {
        String login = "testUser";
        String password = "correctPassword";
        User expectedUser = new User(1, login, password, "USER");
        List<User> users = Collections.singletonList(expectedUser);

        when(mockUserRepository.getUsersByLogin(login)).thenReturn(users);

        User resultUser = userService.validateUser(login, password);

        assertEquals(expectedUser, resultUser);
        verify(mockUserRepository, times(1)).getUsersByLogin(login);
    }

    @Test
    @DisplayName("Валидация пользователя - пользователь существует, но пароль неверен")
    void testValidateUser_UserExistsButIncorrectPassword_ThrowsIncorrectPasswordException()
            throws PersistException, NoSuchUserExistsException {
        String login = "testUser";
        String password = "incorrectPassword";
        User existingUser = new User(1, login, "correctPassword", "USER");
        List<User> users = Collections.singletonList(existingUser);

        when(mockUserRepository.getUsersByLogin(login)).thenReturn(users);

        assertThrows(IncorrectPasswordException.class, () -> userService.validateUser(login, password));
        verify(mockUserRepository, times(1)).getUsersByLogin(login);
    }

    @Test
    @DisplayName("Валидация пользователя - пользователь не существует")
    void testValidateUser_UserDoesNotExist_ThrowsNoSuchUserExistsException()
            throws PersistException {
        String login = "nonExistingUser";

        when(mockUserRepository.getUsersByLogin(login)).thenReturn(Collections.emptyList());

        assertThrows(NoSuchUserExistsException.class, () -> userService.validateUser(login, "anyPassword"));
        verify(mockUserRepository, times(1)).getUsersByLogin(login);
    }

    @Test
    @DisplayName("Регистрация пользователя - пользователь уже существует")
    void testRegisterUser_UserAlreadyExists_ThrowsPersistException()
            throws Exception {
        String login = "existingUser";

        when(mockUserRepository.getUsersByLogin(login)).thenReturn(Collections.singletonList(
                new User(1, login, "password", "USER")));

        assertThrows(PersistException.class, () -> userService.registerUser(login, "password"));
        verify(mockUserRepository, times(1)).getUsersByLogin(login);
        verify(mockUserRepository, never()).create(any());
    }

    @Test
    @DisplayName("Регистрация пользователя - все данные корректны")
    void testRegisterUser_AllDataCorrect_UserSuccessfullyRegistered()
            throws PersistException, SQLException, WrongDataException {
        String login = "newUser";
        String password = "password";
        when(mockUserRepository.getUsersByLogin(login)).thenReturn(Collections.emptyList());

        userService.registerUser(login, password);

        verify(mockUserRepository, times(1)).getUsersByLogin(login);
        verify(mockUserRepository, times(1)).create(any(User.class));
    }
}