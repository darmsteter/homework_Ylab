package com.coworking_service.service.interfaces;

import com.coworking_service.entity.User;
import com.coworking_service.exception.IncorrectPasswordException;
import com.coworking_service.exception.NoSuchUserExistsException;
import com.coworking_service.exception.PersistException;
import com.coworking_service.exception.WrongDataException;

import java.sql.SQLException;


/**
 * Интерфейс для работы с пользователями в системе.
 */
public interface UserService {
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
    User validateUser(String login, String password)
            throws NoSuchUserExistsException,
            IncorrectPasswordException,
            PersistException;

    /**
     * Регистрирует нового пользователя в системе.
     *
     * @param login    логин нового пользователя
     * @param password пароль нового пользователя
     * @throws PersistException   если пользователь с указанным логином уже существует
     * @throws WrongDataException если данные пользователя неверны
     * @throws SQLException       если произошла ошибка при доступе к базе данных
     */
    void registerUser(String login, String password)
            throws PersistException, WrongDataException, SQLException;
}
