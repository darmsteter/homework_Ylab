package com.coworking_service.util;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Утилитарный класс для работы с консолью.
 */
public class ConsoleUtil {
    /**
     * Считывает строку, введённую пользователем с консоли через указанный сканнер.
     *
     * @param scanner объект Scanner для чтения ввода с консоли
     * @return введённая пользователем строка
     */
    public static String getInput(Scanner scanner) {
        return scanner.nextLine();
    }

    /**
     * Считывает число, введённое пользователем с консоли через указанный сканнер.
     *
     * @param scanner объект Scanner для чтения ввода с консоли
     * @return введённое пользователем число
     */
    public static int getInputInt(Scanner scanner) {
        while (true) {
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                scanner.next();
            }
        }
    }
}