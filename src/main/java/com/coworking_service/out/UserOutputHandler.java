package com.coworking_service.out;

import com.coworking_service.entity.User;
import com.coworking_service.entity.enums.Role;
import com.coworking_service.exception.PersistException;
import com.coworking_service.in.UserInputHandler;
import com.coworking_service.entity.enums.MessageType;
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
     * Приветствует онлайн-пользователя и определяет его роль для предоставления вариантов действий.
     *
     * @param onlineUser объект пользователя
     */
    public void greetingsForOnlineUser(User onlineUser) throws PersistException {
        if (onlineUser.role().equals(Role.ADMINISTRATOR.name())) {
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
    public void greetingsForUser(User onlineUser) throws PersistException {
        ConsoleUtil.printMessage(MessageType.ACTIONS_FOR_USER);
        userInputHandler.handleUserActions(onlineUser);
    }

    /**
     * Приветствует администратора и предоставляет ему варианты действий.
     *
     * @param onlineUser объект пользователя
     */
    public void greetingsForAdmin(User onlineUser) throws PersistException {
        ConsoleUtil.printMessage(MessageType.ACTIONS_FOR_ADMINISTRATOR);
        userInputHandler.handleAdminActions(onlineUser);
    }
}