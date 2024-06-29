package com.coworking_service.model.enums;

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

    /**
     * Получает строковое представление роли.
     *
     * @return строковое представление роли
     */
    public String getRole() {
        return role;
    }
}
