package com.coworking_service.exception;

import static java.lang.String.format;

/**
 * Исключение, которое выбрасывается, если не получается создать бронь.
 */
public class NoBookingException extends RuntimeException {
    /**
     * Создает исключение с сообщением, указывающим, что бронь не может быть создана.
     *
     * @param message причина, по которой не может быть создана бронь
     */
    public NoBookingException(String message) {
        super(format("Бронь не может быть создана + '%s'", message));
    }
}
