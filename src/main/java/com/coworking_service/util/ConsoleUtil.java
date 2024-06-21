package com.coworking_service.util;

import com.coworking_service.model.enums.MessageType;

import java.util.Scanner;

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

    /**
     * Считывает строку, введённую пользователем с консоли через указанный сканнер.
     *
     * @param scanner объект Scanner для чтения ввода с консоли
     * @return введённая пользователем строка
     */
    public static String getInput(Scanner scanner) {
        return scanner.nextLine();
    }
}