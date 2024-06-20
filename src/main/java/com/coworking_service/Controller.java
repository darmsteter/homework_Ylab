package com.coworking_service;

import com.coworking_service.exception.NoSuchUserExistsException;
import com.coworking_service.model.User;
import com.coworking_service.model.UserDirectory;
import com.coworking_service.model.enums.MessageType;
import com.coworking_service.service.UserDirectoryServiceImpl;
import com.coworking_service.service.interfaces.UserDirectoryService;
import com.coworking_service.util.ConsoleUtil;

import java.util.Scanner;

public class Controller {

    private final UserDirectoryService userDirectoryService = new UserDirectoryServiceImpl(
            new UserDirectory(
                    new User("admin", "admin")
            )
    );

    public void console() {
        Scanner scan = new Scanner(System.in);
        ConsoleUtil.printMessage(MessageType.GREETINGS);
        String greetings = scan.nextLine();
        if (greetings.equalsIgnoreCase("R")) {
            System.out.println("регистрация");
        } else if (greetings.equalsIgnoreCase("E")) {
            logIn(scan);
        } else {
            System.out.println("неправильная комманда, повторите ввод");
        }
    }

    public void logIn(Scanner scan) throws NoSuchUserExistsException {
        ConsoleUtil.printMessage(MessageType.ENTRANCE_LOGIN);

        boolean correctLogin = false;

        while (!correctLogin) {
            String login = scan.nextLine();
            if (userDirectoryService.checkIsUserExist(login)) {
                correctLogin = true;
                User currentUser = userDirectoryService.findUserByLogin(login);

                boolean correctPassword = false;
                ConsoleUtil.printMessage(MessageType.ENTRANCE_PASSWORD);
                while (!correctPassword) {
                    String password = scan.nextLine();
                    if (password.equals(currentUser.password())) {
                        ConsoleUtil.printMessage(MessageType.ENTRANCE);
                        System.out.println(currentUser.login());
                        correctPassword = true;
                    } else {
                        ConsoleUtil.printMessage(MessageType.ENTRANCE_PASSWORD_ERROR);
                    }
                }
            } else {
                ConsoleUtil.printMessage(MessageType.ENTRANCE_LOGIN_ERROR);
            }
        }
    }
}
