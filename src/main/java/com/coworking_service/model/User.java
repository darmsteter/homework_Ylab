package com.coworking_service.model;

import com.coworking_service.model.enums.Role;

/**
 * Класс, представляющий пользователя в системе.
 */
public record User(String login, String password, Role role) {
}
