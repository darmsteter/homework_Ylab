package com.coworking_service;

import com.coworking_service.entity.User;
import com.coworking_service.exception.PersistException;
import com.coworking_service.exception.WrongDataException;
import com.coworking_service.in.UserInputHandler;
import com.coworking_service.entity.enums.MessageType;
import com.coworking_service.out.UserOutputHandler;
import com.coworking_service.repository.UserRepository;

import java.util.List;

/**
 * Контроллер для управления консольным интерфейсом приложения коворкинг-сервиса.
 */
public class Controller {
    private final UserInputHandler userInputHandler = new UserInputHandler();
    private final UserOutputHandler userOutputHandler = new UserOutputHandler(
            userInputHandler
    );

    private final UserRepository userRepository = new UserRepository();

    /**
     * Запускает консольное приложение.
     */
    public void console() throws PersistException, WrongDataException {
        System.out.println(MessageType.WELCOME.getMessage());

        User onlineUser = null;

        while (true) {
            if (onlineUser == null) {
                String onlineUserLogin = userInputHandler.greeting();

                if (onlineUserLogin.isEmpty()) {
                    break;
                }

                try {
                    List<User> users = userRepository.getUsersByLogin(onlineUserLogin);
                    if (users.isEmpty()) {
                        System.out.println(MessageType.LOGIN_NOT_FOUND_ERROR.getMessage());
                    } else {
                        onlineUser = users.get(0);
                        userOutputHandler.greetingsForOnlineUser(onlineUser);
                    }
                } catch (PersistException e) {
                    System.out.println(MessageType.LOGIN_NOT_FOUND_ERROR.getMessage());
                } catch (WrongDataException e) {
                    throw new RuntimeException(e);
                }
            } else {
                userOutputHandler.greetingsForOnlineUser(onlineUser);
                onlineUser = null;
            }
        }
    }

}