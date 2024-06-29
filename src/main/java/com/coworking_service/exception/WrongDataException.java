package com.coworking_service.exception;

/**
 * Исключение, которое выбрасывается при обнаружении неверных данных.
 */
public class WrongDataException extends Exception {
    /**
     * Создает новое исключение WrongDataException с заданным сообщением.
     *
     * @param message сообщение, объясняющее причину исключения.
     */
    public WrongDataException(String message) {
        super(message);
    }
}