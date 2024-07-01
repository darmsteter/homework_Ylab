package com.coworking_service.out;

import com.coworking_service.entity.User;
import com.coworking_service.entity.enums.Role;
import com.coworking_service.exception.PersistException;
import com.coworking_service.exception.WrongDataException;
import com.coworking_service.in.UserInputHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

@DisplayName("Тесты для UserOutputHandler")
public class UserOutputHandlerTest {

    @Mock
    private UserInputHandler userInputHandler;

    @InjectMocks
    private UserOutputHandler userOutputHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Тест метода greetingsForOnlineUser для роли ADMINISTRATOR")
    void testGreetingsForOnlineUser_Administrator() throws PersistException, WrongDataException {
        User adminUser = new User(1, "test", "test", Role.ADMINISTRATOR.name());

        userOutputHandler.greetingsForOnlineUser(adminUser);

        verify(userInputHandler, times(1)).handleAdminActions(adminUser);
    }

    @Test
    @DisplayName("Тест метода greetingsForOnlineUser для роли USER")
    void testGreetingsForOnlineUser_User() throws PersistException, WrongDataException {
        User regularUser = new User(1, "test", "test", Role.USER.name());

        userOutputHandler.greetingsForOnlineUser(regularUser);

        verify(userInputHandler, times(1)).handleUserActions(regularUser);
    }

    @Test
    @DisplayName("Тест метода greetingsForUser")
    void testGreetingsForUser() throws PersistException {
        User regularUser = new User(1, "test", "test", Role.USER.name());

        userOutputHandler.greetingsForUser(regularUser);

        verify(userInputHandler, times(1)).handleUserActions(regularUser);
    }

    @Test
    @DisplayName("Тест метода greetingsForAdmin")
    void testGreetingsForAdmin() throws PersistException {
        User adminUser = new User(1, "test", "test", Role.ADMINISTRATOR.name());

        userOutputHandler.greetingsForAdmin(adminUser);

        verify(userInputHandler, times(1)).handleAdminActions(adminUser);
    }
}
