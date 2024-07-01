package com.coworking_service;

import com.coworking_service.entity.User;
import com.coworking_service.exception.PersistException;
import com.coworking_service.exception.WrongDataException;
import com.coworking_service.in.UserInputHandler;
import com.coworking_service.out.UserOutputHandler;
import com.coworking_service.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class ControllerTest {

    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private UserOutputHandler mockUserOutputHandler;

    @Mock
    private UserInputHandler mockUserInputHandler;

    private Controller controller;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        controller = new Controller();
        controller.setUserRepository(mockUserRepository);
        controller.setUserOutputHandler(mockUserOutputHandler);
        controller.setUserInputHandler(mockUserInputHandler);
    }

    @Test
    @DisplayName("Тест на пустой ввод при авторизации пользователя в консоли")
    public void testConsoleOnlineUserLoginEmpty() throws PersistException, WrongDataException {
        when(mockUserInputHandler.greeting()).thenReturn("");

        controller.console();

        verify(mockUserInputHandler, times(1)).greeting();
        verify(mockUserOutputHandler, never()).greetingsForOnlineUser(any(User.class));
    }
}
