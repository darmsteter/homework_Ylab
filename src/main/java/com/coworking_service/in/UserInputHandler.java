package com.coworking_service.in;

import com.coworking_service.exception.NoSuchUserExistsException;
import com.coworking_service.model.User;
import com.coworking_service.model.enums.Commands;
import com.coworking_service.model.enums.MessageType;
import com.coworking_service.model.enums.Role;
import com.coworking_service.service.interfaces.UserDirectoryService;
import com.coworking_service.util.ConsoleUtil;

import java.util.Scanner;

/**
 * Класс для обработки вводимых пользователем данных.
 */
public class UserInputHandler {
    private final UserDirectoryService userDirectoryService;
    private final Scanner scan = new Scanner(System.in);

    /**
     * Конструктор класса UserInputHandler.
     *
     * @param userDirectoryService сервис для работы с пользователями
     */
    public UserInputHandler(UserDirectoryService userDirectoryService) {
        this.userDirectoryService = userDirectoryService;
    }

    /**
     * Приветствие пользователя и вход в систему или регистрация.
     *
     * @return логин онлайн пользователя
     */
    public String greeting() {
        String onlineUserLogin = "";

        while (onlineUserLogin.isEmpty()) {
            ConsoleUtil.printMessage(MessageType.INSTRUCTIONS);
            String userResponse = ConsoleUtil.getInput(scan);

            switch (userResponse.toUpperCase()) {
                case "R":
                    registration();
                    break;
                case "L":
                    onlineUserLogin = logIn();
                    break;
                default:
                    ConsoleUtil.printMessage(MessageType.INVALID_COMMAND_ERROR);
                    break;
            }
        }
        return onlineUserLogin;
    }

    /**
     * Вход пользователя в систему.
     *
     * @return логин пользователя
     * @throws NoSuchUserExistsException если пользователь с указанным логином не найден
     */
    private String logIn() throws NoSuchUserExistsException {
        ConsoleUtil.printMessage(MessageType.RETURN_TO_START_PAGE);
        ConsoleUtil.printMessage(MessageType.PROMPT_LOGIN);

        String login = ConsoleUtil.getInput(scan);
        if (login.equalsIgnoreCase(Commands.START_PAGE.getCommand())) {
            return "";
        }
        if (userDirectoryService.checkIsUserExist(login)) {
            User currentUser = userDirectoryService.findUserByLogin(login);

            ConsoleUtil.printMessage(MessageType.RETURN_TO_START_PAGE);
            ConsoleUtil.printMessage(MessageType.PROMPT_PASSWORD);

            while (true) {
                String password = ConsoleUtil.getInput(scan);

                if (password.equals(currentUser.password())) {
                    ConsoleUtil.printMessage(MessageType.WELCOME_USER);
                    System.out.println(currentUser.login());
                    return currentUser.login();
                } else if (password.equalsIgnoreCase(Commands.START_PAGE.getCommand())) {
                    return "";
                } else {
                    ConsoleUtil.printMessage(MessageType.RETURN_TO_START_PAGE);
                    ConsoleUtil.printMessage(MessageType.INCORRECT_PASSWORD_ERROR);
                }
            }
        } else {
            ConsoleUtil.printMessage(MessageType.RETURN_TO_START_PAGE);
            ConsoleUtil.printMessage(MessageType.LOGIN_NOT_FOUND_ERROR);
        }
        return "";
    }

    /**
     * Регистрация нового пользователя.
     */
    public void registration() {
        ConsoleUtil.printMessage(MessageType.RETURN_TO_START_PAGE);
        ConsoleUtil.printMessage(MessageType.PROMPT_LOGIN);

        String login = ConsoleUtil.getInput(scan);

        if (login.equalsIgnoreCase(Commands.START_PAGE.getCommand())) {
            return;
        }

        if (userDirectoryService.checkIsUserExist(login)) {
            ConsoleUtil.printMessage(MessageType.RETURN_TO_START_PAGE);
            ConsoleUtil.printMessage(MessageType.LOGIN_ALREADY_EXISTS_ERROR);
        } else {
            ConsoleUtil.printMessage(MessageType.RETURN_TO_START_PAGE);
            ConsoleUtil.printMessage(MessageType.PROMPT_PASSWORD);

            String password = ConsoleUtil.getInput(scan);

            if (password.equalsIgnoreCase(Commands.START_PAGE.getCommand())) {
                return;
            }

            userDirectoryService.userDirectory().addNewUser(
                    login, new User(login, password, Role.USER)
            );

            ConsoleUtil.printMessage(MessageType.REGISTRATION_SUCCESS);
        }
    }
}