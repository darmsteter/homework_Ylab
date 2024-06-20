package com.coworking_service.model.enums;

public enum MessageType {
    GREETINGS
            ("Добро пожаловать в управление коворкинг-пространством!\n" +
                    "Уже зарегестрированны? Введите E для входа или R для регистрации."),
    ENTRANCE_LOGIN("Введите логин:"),
    ENTRANCE_LOGIN_ERROR("Пользователь не найден, введите корректный логин:"),
    ENTRANCE_PASSWORD("Введите пароль:"),
    ENTRANCE_PASSWORD_ERROR("Неверный пароль, попробуйте снова:"),
    ENTRANCE("Добро пожаловать,");

    private final String message;

    MessageType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
