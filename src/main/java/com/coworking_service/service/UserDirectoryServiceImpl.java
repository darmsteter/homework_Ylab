package com.coworking_service.service;

import com.coworking_service.exception.IncorrectPasswordException;
import com.coworking_service.exception.NoSuchUserExistsException;
import com.coworking_service.exception.UserAlreadyExistsException;
import com.coworking_service.model.User;
import com.coworking_service.model.enums.Commands;
import com.coworking_service.model.enums.MessageType;
import com.coworking_service.model.enums.Role;
import com.coworking_service.repository.collections_repository.UserDirectory;
import com.coworking_service.service.interfaces.UserDirectoryService;
import com.coworking_service.util.ConsoleUtil;

import java.util.Scanner;

/**
 * Реализация сервиса для работы с директорией пользователей.
 */
public record UserDirectoryServiceImpl(UserDirectory userDirectory) implements UserDirectoryService {
    /**
     * Находит пользователя по логину.
     *
     * @param login логин пользователя для поиска
     * @return найденный пользователь
     * @throws NoSuchUserExistsException если пользователь с указанным логином не найден
     */
    public User findUserByLogin(String login) throws NoSuchUserExistsException {
        return userDirectory.findByLogin(login);
    }

    /**
     * Проверяет, существует ли пользователь с указанным логином.
     *
     * @param login логин пользователя для проверки
     * @return true, если пользователь существует, иначе false
     */
    public boolean checkIsUserExist(String login) {
        return userDirectory.isLoginExist(login);
    }

    /**
     * Возвращает логин пользователя после успешного входа в систему.
     *
     * @param login   логин пользователя для входа
     * @param scanner объект Scanner для получения ввода от пользователя
     * @return логин пользователя
     * @throws NoSuchUserExistsException  если пользователь с указанным логином не найден
     * @throws IncorrectPasswordException если введен неверный пароль
     */
    public String logIn(String login, Scanner scanner) throws NoSuchUserExistsException, IncorrectPasswordException {
        if (!checkIsUserExist(login)) {
            throw new NoSuchUserExistsException("Пользователь с логином " + login + " не найден.");
        }

        User currentUser = findUserByLogin(login);
        ConsoleUtil.printMessage(MessageType.RETURN_TO_START_PAGE);
        ConsoleUtil.printMessage(MessageType.PROMPT_PASSWORD);

        while (true) {
            String password = ConsoleUtil.getInput(scanner);
            if (password.equals(currentUser.password())) {
                return currentUser.login();
            } else if (password.equalsIgnoreCase(Commands.START_PAGE.getCommand())) {
                throw new IncorrectPasswordException("Получена команда для возвращения на стартовую страницу.");
            } else {
                ConsoleUtil.printMessage(MessageType.RETURN_TO_START_PAGE);
                ConsoleUtil.printMessage(MessageType.INCORRECT_PASSWORD_ERROR);
            }
        }
    }

    /**
     * Регистрирует нового пользователя с указанным логином и паролем.
     *
     * @param login   логин нового пользователя
     * @param scanner объект Scanner для получения ввода от пользователя
     * @throws UserAlreadyExistsException если пользователь с таким логином уже существует
     */
    public void registerUser(String login, Scanner scanner) throws UserAlreadyExistsException {
        if (checkIsUserExist(login)) {
            throw new UserAlreadyExistsException("User with login " + login + " already exists.");
        }

        ConsoleUtil.printMessage(MessageType.RETURN_TO_START_PAGE);
        ConsoleUtil.printMessage(MessageType.PROMPT_PASSWORD);

        String password = ConsoleUtil.getInput(scanner);

        if (password.equalsIgnoreCase(Commands.START_PAGE.getCommand())) {
            return;
        }

        userDirectory().addNewUser(login, new User(login, password, Role.USER));
    }
}
