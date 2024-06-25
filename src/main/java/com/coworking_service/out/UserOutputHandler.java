package com.coworking_service.out;

import com.coworking_service.in.UserInputHandler;
import com.coworking_service.model.*;
import com.coworking_service.model.enums.MessageType;
import com.coworking_service.model.enums.Role;
import com.coworking_service.util.ConsoleUtil;

/**
 * Класс для обработки вывода данных пользователю в консольном приложении.
 */
public class UserOutputHandler {
    private final UserInputHandler userInputHandler;

    /**
     * Конструктор для создания объекта UserOutputHandler.
     *
     */
    public UserOutputHandler(UserInputHandler userInputHandler) {
        this.userInputHandler = userInputHandler;
    }
    /**
     * Приветствует пользователя в зависимости от его роли (администратор или обычный пользователь).
     *
     * @param onlineUser объект пользователя
     */
    public void greetingsForOnlineUser(User onlineUser) {
        if (onlineUser.role().equals(Role.ADMINISTRATOR)) {
            greetingsForAdmin(onlineUser);
        } else {
            greetingsForUser(onlineUser);
        }
    }

    /**
     * Приветствует обычного пользователя и предоставляет ему варианты действий.
     *
     * @param onlineUser объект пользователя
     */
    public void greetingsForUser(User onlineUser) {
        ConsoleUtil.printMessage(MessageType.ACTIONS_FOR_USER);
        userInputHandler.handleUserActions(onlineUser);
    }

    /**
     * Приветствует администратора и предоставляет ему варианты действий.
     *
     * @param onlineUser объект пользователя
     */
    public void greetingsForAdmin(User onlineUser) {
        ConsoleUtil.printMessage(MessageType.ACTIONS_FOR_ADMINISTRATOR);
        userInputHandler.handleAdminActions(onlineUser);
    }
}