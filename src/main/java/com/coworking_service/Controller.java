package com.coworking_service;

import com.coworking_service.exception.NoSuchUserExistsException;
import com.coworking_service.in.UserInputHandler;
import com.coworking_service.model.*;
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

    private final UserInputHandler userInputHandler = new UserInputHandler(userDirectoryService, coworkingSpace);
    private final UserOutputHandler userOutputHandler = new UserOutputHandler(userDirectoryService, coworkingSpace);

    /**
     * Запускает консольное приложение.
     */
    public void console() {
        coworkingSpace.addConferenceRoom(10);
        coworkingSpace.addIndividualWorkplace();
        coworkingSpace.addIndividualWorkplace();
        ConsoleUtil.printMessage(MessageType.WELCOME);

        String onlineUserLogin = userInputHandler.greeting();

        User onlineUser;
        try {
            onlineUser = userDirectoryService.findUserByLogin(onlineUserLogin);
            userOutputHandler.greetingsForOnlineUser(onlineUser);

        } catch (NoSuchUserExistsException e) {
            ConsoleUtil.printMessage(MessageType.LOGIN_NOT_FOUND_ERROR);
        }
    }


}
