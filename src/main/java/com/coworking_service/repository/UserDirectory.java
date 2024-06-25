package com.coworking_service.repository;

import com.coworking_service.exception.NoSuchUserExistsException;
import com.coworking_service.model.User;

import java.util.LinkedHashMap;
import java.util.Optional;

/**
 * Класс, представляющий директорию пользователей.
 * Обеспечивает управление пользователями, включая добавление, проверку и поиск.
 */
public class UserDirectory {
    private final LinkedHashMap<String, User> mapOfUsers = new LinkedHashMap<>();

    /**
     * Создает директорию пользователей с начальным пользователем.
     *
     * @param user начальный пользователь для добавления в директорию
     */
    public UserDirectory(User user) {
        mapOfUsers.put(user.login(), user);
    }

    /**
     * Проверяет, существует ли пользователь с данным логином.
     *
     * @param login логин для проверки
     * @return true, если пользователь с данным логином существует, иначе false
     */
    public boolean isLoginExist(String login) {
        return mapOfUsers.containsKey(login);
    }

    /**
     * Находит пользователя по логину.
     *
     * @param login логин для поиска
     * @return найденный пользователь
     * @throws NoSuchUserExistsException если пользователь с данным логином не найден
     */
    public User findByLogin(String login) throws NoSuchUserExistsException {
        return Optional.ofNullable(login)
                .map(mapOfUsers::get)
                .orElseThrow(() -> new NoSuchUserExistsException(login));
    }

    /**
     * Добавляет нового пользователя в директорию.
     *
     * @param login логин нового пользователя
     * @param user  пользователь для добавления
     */
    public void addNewUser(String login, User user) {
        mapOfUsers.put(login, user);
    }
}
