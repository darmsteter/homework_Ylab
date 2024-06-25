package com.coworking_service.service.interfaces;

import com.coworking_service.exception.NoSuchUserExistsException;
import com.coworking_service.model.User;
import com.coworking_service.repository.UserDirectory;

/**
 * Интерфейс сервиса для работы с директорией пользователей.
 * Предоставляет методы для проверки существования пользователя,
 * поиска пользователя по логину и получения директории пользователей.
 */
public interface UserDirectoryService {
    /**
     * Проверяет, существует ли пользователь с указанным логином.
     *
     * @param login логин пользователя для проверки
     * @return true, если пользователь существует, иначе false
     */
    boolean checkIsUserExist(String login);

    /**
     * Находит пользователя по его логину.
     *
     * @param login логин пользователя для поиска
     * @return найденный пользователь
     * @throws NoSuchUserExistsException если пользователь с указанным логином не найден
     */
    User findUserByLogin(String login) throws NoSuchUserExistsException;

    /**
     * Получает директорию пользователей.
     *
     * @return директория пользователей
     */
    UserDirectory userDirectory();
}
