package com.coworking_service.out;

import com.coworking_service.entity.User;
import com.coworking_service.entity.enums.Role;
import com.coworking_service.exception.PersistException;
import com.coworking_service.exception.WrongDataException;
import com.coworking_service.in.UserInputHandler;
import com.coworking_service.entity.enums.MessageType;

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
    public void greetingsForOnlineUser(User onlineUser) throws PersistException, WrongDataException {
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
        System.out.println(MessageType.ACTIONS_FOR_USER.getMessage());
        userInputHandler.handleUserActions(onlineUser);
    }

    /**
     * Приветствует администратора и предоставляет ему варианты действий.
     *
     * @param onlineUser объект пользователя
     */
    public void greetingsForAdmin(User onlineUser) throws PersistException {
        System.out.println(MessageType.ACTIONS_FOR_ADMINISTRATOR.getMessage());
        userInputHandler.handleAdminActions(onlineUser);
    }
}