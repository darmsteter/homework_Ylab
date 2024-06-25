package com.coworking_service;

import com.coworking_service.exception.NoSuchUserExistsException;
import com.coworking_service.in.UserInputHandler;
import com.coworking_service.repository.BookingDirectory;
import com.coworking_service.model.CoworkingSpace;
import com.coworking_service.model.User;
import com.coworking_service.repository.UserDirectory;
import com.coworking_service.model.enums.MessageType;
import com.coworking_service.model.enums.Role;
import com.coworking_service.out.UserOutputHandler;
import com.coworking_service.service.UserDirectoryServiceImpl;
import com.coworking_service.service.interfaces.UserDirectoryService;
import com.coworking_service.util.ConsoleUtil;

/**
 * Контроллер для управления консольным интерфейсом приложения коворкинг-сервиса.
 */
public class Controller {

    private final UserDirectoryService userDirectoryService = new UserDirectoryServiceImpl(
            new UserDirectory(
                    new User("admin", "admin", Role.ADMINISTRATOR)
            )
    );

    private final CoworkingSpace coworkingSpace = new CoworkingSpace();
    private final BookingDirectory bookingDirectory = new BookingDirectory();

    private final UserInputHandler userInputHandler =
            new UserInputHandler(
                    userDirectoryService,
                    coworkingSpace,
                    bookingDirectory);
    private final UserOutputHandler userOutputHandler =
            new UserOutputHandler(
                    userDirectoryService,
                    coworkingSpace,
                    bookingDirectory,
                    System.in,
                    System.out
            );

    /**
     * Запускает консольное приложение.
     */
    public void console() {
        coworkingSpace.addConferenceRoom(10);
        coworkingSpace.addIndividualWorkplace();
        coworkingSpace.addIndividualWorkplace();
        ConsoleUtil.printMessage(MessageType.WELCOME);

        User onlineUser = null;
        boolean running = true;

        while (running) {
            if (onlineUser == null) {
                String onlineUserLogin = userInputHandler.greeting();

                if (onlineUserLogin.isEmpty()) {
                    break;
                }

                try {
                    onlineUser = userDirectoryService.findUserByLogin(onlineUserLogin);
                    userOutputHandler.greetingsForOnlineUser(onlineUser);
                    onlineUser = null;

                } catch (NoSuchUserExistsException e) {
                    ConsoleUtil.printMessage(MessageType.LOGIN_NOT_FOUND_ERROR);
                }
            } else {
                userOutputHandler.greetingsForOnlineUser(onlineUser);

                onlineUser = null;
            }
        }
    }
}