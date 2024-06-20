package com.coworking_service.util;

import com.coworking_service.model.enums.MessageType;

public class ConsoleUtil {
    public static void printMessage(MessageType messageType) {
        System.out.println(messageType.getMessage());
    }
}