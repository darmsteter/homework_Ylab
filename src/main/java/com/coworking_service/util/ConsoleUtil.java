package com.coworking_service.util;

import com.coworking_service.model.enums.MessageType;

/**
 * Утилитарный класс для работы с консолью.
 */
public class ConsoleUtil {

    /**
     * Выводит сообщение на консоль.
     *
     * @param messageType тип сообщения для вывода
     */
    public static void printMessage(MessageType messageType) {
        System.out.println(messageType.getMessage());
    }
}