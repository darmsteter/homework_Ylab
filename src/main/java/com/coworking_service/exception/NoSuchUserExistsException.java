package com.coworking_service.exception;

import static java.lang.String.format;

/**
 * Исключение, которое выбрасывается, если пользователь с указанным логином не найден.
 */
public class NoSuchUserExistsException extends RuntimeException {

    /**
     * Создает исключение с сообщением, указывающим, что пользователь с данным логином не найден.
     *
     * @param login логин пользователя, который не был найден
     */
    public NoSuchUserExistsException(String login) {
        super(format("Пользователь с данным логином не найден: + '%s'", login));
    }
}

