package com.coworking_service.exception;

/**
 * Исключение, которое выбрасывается при вводе неправильного пароля.
 */
public class IncorrectPasswordException extends Exception {

    /**
     * Создает исключение с сообщением, указывающим, что пароль неверный.
     *
     * @param message сообщение об ошибке
     */
    public IncorrectPasswordException(String message) {
        super(message);
    }
}