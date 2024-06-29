package com.coworking_service.repository.jdbc_repository.interfaces;


import com.coworking_service.entity.Entity;
import com.coworking_service.exception.PersistException;
import com.coworking_service.exception.WrongDataException;

import java.sql.SQLException;
import java.util.List;

/**
 * Интерфейс репозитория для управления CRUD операциями с сущностями.
 *
 * @param <T>  тип сущности, расширяющей интерфейс Entity
 * @param <PK> тип первичного ключа сущности
 */
public interface Repository<T extends Entity<PK>, PK> {
    /**
     * Получает сущность по ее первичному ключу.
     *
     * @param primaryKey первичный ключ сущности
     * @return сущность, соответствующая заданному первичному ключу
     * @throws PersistException если произошла ошибка при получении сущности
     */
    T getById(PK primaryKey) throws PersistException;

    /**
     * Обновляет существующую сущность в базе данных.
     *
     * @param obj обновляемая сущность
     * @throws PersistException   если произошла ошибка при обновлении сущности
     * @throws WrongDataException если данные сущности неверны
     * @throws SQLException       если произошла ошибка SQL
     */
    void update(T obj) throws PersistException, WrongDataException, SQLException;

    /**
     * Удаляет сущность из базы данных по ее первичному ключу.
     *
     * @param primaryKey первичный ключ сущности
     * @throws PersistException   если произошла ошибка при удалении сущности
     * @throws WrongDataException если данные сущности неверны
     */
    void delete(PK primaryKey) throws PersistException, WrongDataException;

    /**
     * Создает новую сущность в базе данных.
     *
     * @param obj создаваемая сущность
     * @throws PersistException   если произошла ошибка при создании сущности
     * @throws WrongDataException если данные сущности неверны
     * @throws SQLException       если произошла ошибка SQL
     */
    void create(T obj) throws PersistException, WrongDataException, SQLException;

    /**
     * Получает все сущности, разбитые на страницы.
     *
     * @param page номер страницы
     * @return список сущностей на заданной странице
     * @throws PersistException если произошла ошибка при получении сущностей
     */
    List<T> getAll(int page) throws PersistException;

}
