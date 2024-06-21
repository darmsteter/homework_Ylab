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

public class Controller {

    private final UserDirectoryService userDirectoryService = new UserDirectoryServiceImpl(
            new UserDirectory(
                    new User("admin", "admin", Role.ADMINISTRATOR)
            )
    );

    public void console() {
        Scanner scan = new Scanner(System.in);

        ConsoleUtil.printMessage(MessageType.WELCOME);

        String onlineUserLogin = greetings(scan);

        User onlineUser = userDirectoryService.findUserByLogin(onlineUserLogin);
    }

    public String greetings(Scanner scan) {
        String onlineUserLogin = "";

        while (onlineUserLogin.isEmpty()) {
            ConsoleUtil.printMessage(MessageType.GREETINGS);
            String greetings = scan.nextLine();
            if (greetings.equalsIgnoreCase(Commands.REGISTRATION.getCommand())) {
                registration(scan);
            } else if (greetings.equalsIgnoreCase(Commands.LOG_IN.getCommand())) {
                onlineUserLogin = logIn(scan);

            } else {
                ConsoleUtil.printMessage(MessageType.WRONG_COMMAND);
            }
        }
        return onlineUserLogin;
    }

    public String logIn(Scanner scan) throws NoSuchUserExistsException {
        ConsoleUtil.printMessage(MessageType.START_PAGE);
        ConsoleUtil.printMessage(MessageType.LOGIN);

        boolean correctLogin = false;

        while (!correctLogin) {
            String login = scan.nextLine();
            if (login.equalsIgnoreCase(Commands.START_PAGE.getCommand())) {
                break;
            }
            if (userDirectoryService.checkIsUserExist(login)) {
                correctLogin = true;
                User currentUser = userDirectoryService.findUserByLogin(login);

                ConsoleUtil.printMessage(MessageType.START_PAGE);
                ConsoleUtil.printMessage(MessageType.PASSWORD);
                while (true) {
                    String password = scan.nextLine();

                    if (password.equals(currentUser.password())) {
                        if (password.equalsIgnoreCase(Commands.START_PAGE.getCommand())) {
                            break;
                        }
                        ConsoleUtil.printMessage(MessageType.ENTRANCE);
                        System.out.println(currentUser.login());
                        return currentUser.login();
                    } else {
                        if (password.equalsIgnoreCase(Commands.START_PAGE.getCommand())) {
                            break;
                        }
                        ConsoleUtil.printMessage(MessageType.START_PAGE);
                        ConsoleUtil.printMessage(MessageType.ENTRANCE_PASSWORD_ERROR);
                    }
                }
            } else {
                ConsoleUtil.printMessage(MessageType.START_PAGE);
                ConsoleUtil.printMessage(MessageType.ENTRANCE_LOGIN_ERROR);
            }
        }
        return "";
    }

    public void registration(Scanner scan) {
        ConsoleUtil.printMessage(MessageType.START_PAGE);
        ConsoleUtil.printMessage(MessageType.LOGIN);

        boolean correctLogin = false;

        while (!correctLogin) {
            String login = scan.nextLine();

            if (login.equalsIgnoreCase(Commands.START_PAGE.getCommand())) {
                break;
            }

            if (userDirectoryService.checkIsUserExist(login)) {
                ConsoleUtil.printMessage(MessageType.START_PAGE);
                ConsoleUtil.printMessage(MessageType.ENTRANCE_LOGIN_REPEAT);
            } else {
                correctLogin = true;

                ConsoleUtil.printMessage(MessageType.START_PAGE);
                ConsoleUtil.printMessage(MessageType.PASSWORD);

                String password = scan.nextLine();

                if (password.equalsIgnoreCase(Commands.START_PAGE.getCommand())) {
                    break;
                }

                userDirectoryService.getUserDirectory().addNewUser(login, new User(login, password, Role.USER));

                ConsoleUtil.printMessage(MessageType.REGISTRATION_SUCCESS);
            }
        }
    }
}
