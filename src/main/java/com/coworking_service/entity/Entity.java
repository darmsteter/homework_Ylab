package com.coworking_service.entity;


/**
 * Базовый интерфейс для всех сущностей с первичным ключом.
 *
 * @param <PK> тип первичного ключа.
 */
public interface Entity<PK> {
    /**
     * Возвращает уникальный идентификатор сущности.
     *
     * @return первичный ключ сущности.
     */
    PK getPK();
}
