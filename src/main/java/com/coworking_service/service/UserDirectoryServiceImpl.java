package com.coworking_service.service;

import com.coworking_service.exception.NoSuchUserExistsException;
import com.coworking_service.model.User;
import com.coworking_service.model.UserDirectory;
import com.coworking_service.service.interfaces.UserDirectoryService;

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
}
