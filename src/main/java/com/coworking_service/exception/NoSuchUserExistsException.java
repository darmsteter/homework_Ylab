package com.coworking_service.exception;

import static java.lang.String.format;

public class NoSuchUserExistsException extends RuntimeException {

    public NoSuchUserExistsException(String login) {
        super(format("Пользователь с данным логином не найдена: + '%s'", login));
    }
}

