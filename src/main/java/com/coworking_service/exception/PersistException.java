package com.coworking_service.exception;

/**
 * Исключение, которое выбрасывается при ошибках сохранения данных.
 */
public class PersistException extends Exception {
    /**
     * Создает новое исключение PersistException с заданной причиной.
     *
     * @param message причина выброса исключения.
     */
    public PersistException(String message) {
        super(message);
    }

    public PersistException(Exception e) {
        super(e);
    }
}
