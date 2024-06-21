package com.coworking_service.model.enums;

/**
 * Перечисление, представляющее команды, доступные в консольном приложении.
 */
public enum Commands {
    /**
     * Команда для регистрации нового пользователя.
     */
    REGISTRATION("R"),

    /**
     * Команда для входа в систему.
     */
    LOG_IN("L"),

    /**
     * Команда для перехода на стартовую страницу.
     */
    START_PAGE("S");

    private final String command;

    /**
     * Конструктор для инициализации команды.
     *
     * @param command строковое представление команды
     */
    Commands(String command) {
        this.command = command;
    }

    /**
     * Получает строковое представление команды.
     *
     * @return строковое представление команды
     */
    public String getCommand() {
        return command;
    }
}
