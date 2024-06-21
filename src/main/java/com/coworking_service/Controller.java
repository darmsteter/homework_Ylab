package com.coworking_service;

import com.coworking_service.exception.NoSuchUserExistsException;
import com.coworking_service.model.User;
import com.coworking_service.model.UserDirectory;
import com.coworking_service.model.enums.Commands;
import com.coworking_service.model.enums.MessageType;
import com.coworking_service.model.enums.Role;
import com.coworking_service.service.UserDirectoryServiceImpl;
import com.coworking_service.service.interfaces.UserDirectoryService;
import com.coworking_service.util.ConsoleUtil;

import java.util.Scanner;

/**
 * Контроллер для управления консольным интерфейсом приложения коворкинг-сервиса.
 */
public class Controller {

    private final UserDirectoryService userDirectoryService = new UserDirectoryServiceImpl(
            new UserDirectory(
                    new User("admin", "admin", Role.ADMINISTRATOR)
            )
    );

    /**
     * Запускает консольное приложение.
     */
    public void console() {
        Scanner scan = new Scanner(System.in);

        ConsoleUtil.printMessage(MessageType.WELCOME);

        String onlineUserLogin = greetings(scan);

        User onlineUser = userDirectoryService.findUserByLogin(onlineUserLogin);
    }

    /**
     * Приветствие пользователя и вход в систему или регистрация.
     *
     * @param scan объект Scanner для ввода пользователя
     * @return логин онлайн пользователя
     */
    public String greetings(Scanner scan) {
        String onlineUserLogin = "";

        while (onlineUserLogin.isEmpty()) {
            ConsoleUtil.printMessage(MessageType.INSTRUCTIONS);
            String greetings = scan.nextLine();
            if (greetings.equalsIgnoreCase(Commands.REGISTRATION.getCommand())) {
                registration(scan);
            } else if (greetings.equalsIgnoreCase(Commands.LOG_IN.getCommand())) {
                onlineUserLogin = logIn(scan);

            } else {
                ConsoleUtil.printMessage(MessageType.INVALID_COMMAND_ERROR);
            }
        }
        return onlineUserLogin;
    }

    /**
     * Вход пользователя в систему.
     *
     * @param scan объект Scanner для ввода пользователя
     * @return логин пользователя
     * @throws NoSuchUserExistsException если пользователь с указанным логином не найден
     */
    public String logIn(Scanner scan) throws NoSuchUserExistsException {
        ConsoleUtil.printMessage(MessageType.RETURN_TO_START_PAGE);
        ConsoleUtil.printMessage(MessageType.PROMPT_LOGIN);

        boolean correctLogin = false;

        while (!correctLogin) {
            String login = scan.nextLine();
            if (login.equalsIgnoreCase(Commands.START_PAGE.getCommand())) {
                break;
            }
            if (userDirectoryService.checkIsUserExist(login)) {
                correctLogin = true;
                User currentUser = userDirectoryService.findUserByLogin(login);

                ConsoleUtil.printMessage(MessageType.RETURN_TO_START_PAGE);
                ConsoleUtil.printMessage(MessageType.PROMPT_PASSWORD);
                while (true) {
                    String password = scan.nextLine();

                    if (password.equals(currentUser.password())) {
                        if (password.equalsIgnoreCase(Commands.START_PAGE.getCommand())) {
                            break;
                        }
                        ConsoleUtil.printMessage(MessageType.WELCOME_USER);
                        System.out.println(currentUser.login());
                        return currentUser.login();
                    } else {
                        if (password.equalsIgnoreCase(Commands.START_PAGE.getCommand())) {
                            break;
                        }
                        ConsoleUtil.printMessage(MessageType.RETURN_TO_START_PAGE);
                        ConsoleUtil.printMessage(MessageType.INCORRECT_PASSWORD_ERROR);
                    }
                }
            } else {
                ConsoleUtil.printMessage(MessageType.RETURN_TO_START_PAGE);
                ConsoleUtil.printMessage(MessageType.LOGIN_NOT_FOUND_ERROR);
            }
        }
        return "";
    }

    /**
     * Регистрация нового пользователя.
     *
     * @param scan объект Scanner для ввода пользователя
     */
    public void registration(Scanner scan) {
        ConsoleUtil.printMessage(MessageType.RETURN_TO_START_PAGE);
        ConsoleUtil.printMessage(MessageType.PROMPT_LOGIN);

        boolean correctLogin = false;

        while (!correctLogin) {
            String login = scan.nextLine();

            if (login.equalsIgnoreCase(Commands.START_PAGE.getCommand())) {
                break;
            }

            if (userDirectoryService.checkIsUserExist(login)) {
                ConsoleUtil.printMessage(MessageType.RETURN_TO_START_PAGE);
                ConsoleUtil.printMessage(MessageType.LOGIN_ALREADY_EXISTS_ERROR);
            } else {
                correctLogin = true;

                ConsoleUtil.printMessage(MessageType.RETURN_TO_START_PAGE);
                ConsoleUtil.printMessage(MessageType.PROMPT_PASSWORD);

                String password = scan.nextLine();

                if (password.equalsIgnoreCase(Commands.START_PAGE.getCommand())) {
                    break;
                }

                userDirectoryService.userDirectory().addNewUser(login, new User(login, password, Role.USER));

                ConsoleUtil.printMessage(MessageType.REGISTRATION_SUCCESS);
            }
        }
    }
}
