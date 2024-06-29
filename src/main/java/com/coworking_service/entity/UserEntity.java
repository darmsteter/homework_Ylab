package com.coworking_service.entity;

/**
 * Представляет запись о пользователе.
 *
 * @param id       уникальный идентификатор пользователя.
 * @param login    логин пользователя.
 * @param password пароль пользователя.
 * @param role     роль пользователя в системе.
 */
public record UserEntity(Integer id,
                         String login,
                         String password,
                         String role)
        implements Entity<Integer> {

    /**
     * Возвращает уникальный идентификатор пользователя.
     *
     * @return первичный ключ.
     */
    @Override
    public Integer getPK() {
        return id;
    }
}
