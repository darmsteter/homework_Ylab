package com.coworking_service.entity.enums;

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
    START_PAGE("S"),

    /**
     * Команда для поиска бронирования по дате.
     */
    SEARCH_BOOKING_BY_DATE("D"),

    /**
     * Команда для получения списка всех рабочих мест.
     */
    LIST_OF_WORKPLACES("L"),

    /**
     * Команда для создания нового бронирования.
     */
    CREATE_NEW_BOOKING("B"),

    /**
     * Команда для поиска бронирования по логину пользователя.
     */
    SEARCH_BOOKING_BY_LOGIN("M"),

    /**
     * Команда для выхода из системы.
     */
    EXIT("E"),

    /**
     * Команда для удаления бронирования.
     */
    DELETE_BOOKING("R"),

    /**
     * Команда для создания нового индивидуального рабочего места.
     */
    CREATE_INDIVIDUAL_WORKPLACE("I"),

    /**
     * Команда для создания нового конференц-зала.
     */
    CREATE_CONFERENCE_ROOM("C"),

    /**
     * Команда для подтверждения действия (Да).
     */
    YES("Y"),

    /**
     * Команда для отмены действия (Нет).
     */
    NO("N");

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

    /**
     * Получает команду по строковому представлению.
     *
     * @param command строковое представление команды
     * @return соответствующий элемент Commands или null, если команда не найдена
     */
    public static Commands fromString(String command) {
        for (Commands cmd : Commands.values()) {
            if (cmd.getCommand().equalsIgnoreCase(command)) {
                return cmd;
            }
        }
        return null;
    }
}
