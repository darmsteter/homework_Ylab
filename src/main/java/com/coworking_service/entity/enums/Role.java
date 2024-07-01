package com.coworking_service.entity.enums;

/**
 * Перечисление, представляющее роли пользователей в приложении.
 */
public enum Role {
    /**
     * Роль администратора.
     */
    ADMINISTRATOR("Администратор"),

    /**
     * Роль пользователя.
     */
    USER("Пользователь");

    private final String role;

    /**
     * Конструктор для инициализации роли.
     *
     * @param role текстовое представление роли
     */
    Role(String role) {
        this.role = role;
    }
}
