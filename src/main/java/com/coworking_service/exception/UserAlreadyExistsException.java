package com.coworking_service.exception;

/**
 * Исключение, которое выбрасывается при попытке добавить уже существующего пользователя.
 */
public class UserAlreadyExistsException extends RuntimeException {
    /**
     * Создает исключение с сообщением, указывающим, что пользователь уже существует.
     *
     * @param message сообщение об ошибке
     */
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
