package com.coworking_service.model.enums;

public enum Role {
    ADMINISTRATOR("Администратор"),
    USER("Пользователь");

    private final String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
