package com.coworking_service.model.enums;

public enum MessageType {
    WELCOME("Добро пожаловать в управление коворкинг-пространством!"),
    GREETINGS("Введите L для входа или R для регистрации."),
    LOGIN("Введите логин:"),
    ENTRANCE_LOGIN_ERROR("Пользователь не найден, введите корректный логин:"),
    ENTRANCE_LOGIN_REPEAT("Пользователь с таким именем уже существует, введите новый логин:"),
    PASSWORD("Введите пароль:"),
    //PASSWORD_REPEAT("Повторите пароль:"),
    ENTRANCE_PASSWORD_ERROR("Неверный пароль, попробуйте снова:"),
    ENTRANCE("Добро пожаловать,"),
    REGISTRATION_SUCCESS("Вы успешно зарегистрировались."),
    START_PAGE("Для возвращения на стартовую страницу введите S."),
    WRONG_COMMAND("Неверная команда, повторите ввод.")
    ;

    private final String message;

    MessageType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
