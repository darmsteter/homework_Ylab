package com.coworking_service;

import com.coworking_service.entity.UserEntity;
import com.coworking_service.exception.PersistException;
import com.coworking_service.in.UserInputHandler;
import com.coworking_service.repository.collections_repository.BookingDirectory;
import com.coworking_service.model.CoworkingSpace;
import com.coworking_service.model.User;
import com.coworking_service.repository.collections_repository.UserDirectory;
import com.coworking_service.model.enums.MessageType;
import com.coworking_service.model.enums.Role;
import com.coworking_service.out.UserOutputHandler;
import com.coworking_service.repository.jdbc_repository.UserRepository;
import com.coworking_service.service.BookingServiceImpl;
import com.coworking_service.service.CoworkingSpaceServiceImpl;
import com.coworking_service.service.UserDirectoryServiceImpl;
import com.coworking_service.service.interfaces.BookingService;
import com.coworking_service.service.interfaces.CoworkingSpaceService;
import com.coworking_service.service.interfaces.UserDirectoryService;
import com.coworking_service.util.ConsoleUtil;

import java.util.List;

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
    private final BookingService bookingService = new BookingServiceImpl(coworkingSpace, bookingDirectory);
    private final CoworkingSpaceService coworkingSpaceService = new CoworkingSpaceServiceImpl(coworkingSpace);

    private final UserInputHandler userInputHandler = new UserInputHandler(
            bookingService,
            userDirectoryService,
            coworkingSpaceService);
    private final UserOutputHandler userOutputHandler = new UserOutputHandler(
            userInputHandler
    );

    private final UserRepository userRepository = new UserRepository();

    /**
     * Запускает консольное приложение.
     */
    public void console() throws PersistException {
        ConsoleUtil.printMessage(MessageType.WELCOME);

        UserEntity onlineUser = null;
        boolean running = true;

        while (running) {
            if (onlineUser == null) {
                String onlineUserLogin = userInputHandler.greeting();

                if (onlineUserLogin.isEmpty()) {
                    break;
                }

                try {
                    List<UserEntity> users = userRepository.getUsersByLogin(onlineUserLogin);
                    if (users.isEmpty()) {
                        ConsoleUtil.printMessage(MessageType.LOGIN_NOT_FOUND_ERROR);
                    } else {
                        onlineUser = users.get(0);
                        userOutputHandler.greetingsForOnlineUser(onlineUser);
                    }
                } catch (PersistException e) {
                    ConsoleUtil.printMessage(MessageType.LOGIN_NOT_FOUND_ERROR);
                }
            } else {
                userOutputHandler.greetingsForOnlineUser(onlineUser);
                onlineUser = null;
            }
        }
    }

}