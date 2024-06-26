package com.coworking_service.service.interfaces;

import com.coworking_service.exception.IncorrectPasswordException;
import com.coworking_service.exception.NoSuchUserExistsException;
import com.coworking_service.exception.UserAlreadyExistsException;
import com.coworking_service.model.User;
import com.coworking_service.repository.UserDirectory;

import java.util.Scanner;

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

    /**
     * Аутентификация пользователя по логину.
     *
     * @param login   логин пользователя
     * @param scanner сканнер для ввода пароля
     * @return логин пользователя
     * @throws NoSuchUserExistsException   если пользователь с указанным логином не найден
     * @throws IncorrectPasswordException если пароль введен неверно
     */
    String logIn(String login, Scanner scanner) throws NoSuchUserExistsException, IncorrectPasswordException;

    /**
     * Регистрация нового пользователя с указанным логином.
     *
     * @param login   логин нового пользователя
     * @param scanner сканнер для ввода дополнительной информации
     * @throws UserAlreadyExistsException если пользователь с таким логином уже существует
     */
    void registerUser(String login, Scanner scanner) throws UserAlreadyExistsException;
}
