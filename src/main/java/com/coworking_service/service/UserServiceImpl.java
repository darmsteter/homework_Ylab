package com.coworking_service.service;

import com.coworking_service.entity.User;
import com.coworking_service.entity.enums.Role;
import com.coworking_service.exception.IncorrectPasswordException;
import com.coworking_service.exception.NoSuchUserExistsException;
import com.coworking_service.exception.PersistException;
import com.coworking_service.exception.WrongDataException;
import com.coworking_service.repository.UserRepository;
import com.coworking_service.service.interfaces.UserService;

import java.sql.SQLException;
import java.util.List;

/**
 * Реализация интерфейса UserService.
 */
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    /**
     * Конструктор для создания экземпляра UserServiceImpl.
     *
     * @param userRepository репозиторий пользователей
     */
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Валидирует учетные данные пользователя.
     *
     * @param login    логин пользователя
     * @param password пароль пользователя
     * @return пользователь, соответствующий указанным учетным данным
     * @throws NoSuchUserExistsException  если пользователь с указанным логином не найден
     * @throws IncorrectPasswordException если пароль неверный
     * @throws PersistException           если произошла ошибка при доступе к базе данных
     */
    public User validateUser(String login, String password)
            throws NoSuchUserExistsException,
            IncorrectPasswordException,
            PersistException {
        List<User> users = userRepository.getUsersByLogin(login);
        if (users.isEmpty()) {
            throw new NoSuchUserExistsException("Пользователь '" + login + "' не найден.");
        }

        User user = users.get(0);

        if (!password.equals(user.password())) {
            throw new IncorrectPasswordException("Неверный пароль для пользователя '" + login + "'.");
        }

        return user;
    }

    /**
     * Регистрирует нового пользователя в системе.
     *
     * @param login    логин нового пользователя
     * @param password пароль нового пользователя
     * @throws PersistException   если пользователь с указанным логином уже существует
     * @throws WrongDataException если данные пользователя неверны
     * @throws SQLException       если произошла ошибка при доступе к базе данных
     */
    public void registerUser(String login, String password)
            throws PersistException, WrongDataException, SQLException {
        List<User> existingUsers = userRepository.getUsersByLogin(login);
        if (existingUsers != null && !existingUsers.isEmpty()) {
            throw new PersistException("Пользователь с логином '" + login + "' уже существует.");
        }

        User newUser = new User(null, login, password, Role.USER.name());
        userRepository.create(newUser);
    }
}
