package com.coworking_service.repository.jdbc_repository;

import com.coworking_service.entity.Entity;
import com.coworking_service.exception.PersistException;
import com.coworking_service.exception.WrongDataException;
import com.coworking_service.repository.jdbc_repository.interfaces.Repository;
import com.coworking_service.util.ConnectionHolder;

import java.io.Serializable;
import java.sql.*;
import java.util.List;

/**
 * Абстрактный класс, предоставляющий базовую реализацию репозитория для работы с базой данных с использованием JDBC.
 *
 * @param <T>  тип сущности, расширяющей интерфейс Entity
 * @param <PK> тип первичного ключа сущности
 */
public abstract class JDBCRepository<T extends Entity<PK>, PK extends Serializable> implements Repository<T, PK> {
    /**
     * Возвращает SQL-запрос для выборки сущности.
     *
     * @return SQL-запрос для выборки сущности
     */
    public abstract String getSelectQuery();

    /**
     * Возвращает SQL-запрос для обновления сущности.
     *
     * @return SQL-запрос для обновления сущности
     */
    public abstract String getUpdateQuery();

    /**
     * Возвращает SQL-запрос для создания новой сущности.
     *
     * @return SQL-запрос для создания новой сущности
     */
    public abstract String getCreateQuery();

    /**
     * Возвращает SQL-запрос для удаления сущности.
     *
     * @return SQL-запрос для удаления сущности
     */
    public abstract String getDeleteQuery();

    /**
     * Подготавливает SQL-выражение для выборки сущности по первичному ключу.
     *
     * @param statement  подготовленное SQL-выражение
     * @param primaryKey первичный ключ сущности
     * @throws SQLException если произошла ошибка при подготовке выражения
     */
    protected abstract void prepareStatementForGetByPK(PreparedStatement statement, PK primaryKey) throws SQLException;

    /**
     * Подготавливает SQL-выражение для обновления сущности.
     *
     * @param statement подготовленное SQL-выражение
     * @param obj       обновляемая сущность
     * @throws SQLException если произошла ошибка при подготовке выражения
     */
    protected abstract void prepareStatementForUpdate(PreparedStatement statement, T obj) throws SQLException;

    /**
     * Подготавливает SQL-выражение для создания новой сущности.
     *
     * @param statement подготовленное SQL-выражение
     * @param obj       создаваемая сущность
     * @throws SQLException если произошла ошибка при подготовке выражения
     */
    protected abstract void prepareStatementForCreate(PreparedStatement statement, T obj) throws SQLException;

    /**
     * Подготавливает SQL-выражение для удаления сущности.
     *
     * @param statement  подготовленное SQL-выражение
     * @param primaryKey первичный ключ сущности
     * @throws SQLException если произошла ошибка при подготовке выражения
     */
    protected abstract void prepareStatementForDelete(PreparedStatement statement, PK primaryKey) throws SQLException;

    /**
     * Проверяет корректность данных перед обновлением сущности.
     *
     * @param obj обновляемая сущность
     * @throws WrongDataException если данные сущности неверны
     * @throws PersistException   если произошла ошибка при проверке
     * @throws SQLException       если произошла ошибка SQL
     */
    protected void checkForUpdate(T obj) throws WrongDataException, PersistException, SQLException {
        if (obj.getPK() == null) {
            throw new WrongDataException("Can't find id to update");
        }
        if (getById(obj.getPK()) == null) {
            throw new WrongDataException("Can't find item to update");
        }
    }

    /**
     * Проверяет корректность данных перед удалением сущности.
     *
     * @param primaryKey первичный ключ удаляемой сущности
     * @throws PersistException   если произошла ошибка при проверке
     * @throws WrongDataException если данные сущности неверны
     */
    protected void checkForDelete(PK primaryKey) throws PersistException, WrongDataException {
        if (getById(primaryKey) == null) {
            throw new WrongDataException("Can't find item to delete");
        }
    }

    /**
     * Проверяет корректность данных перед созданием сущности.
     *
     * @param obj создаваемая сущность
     * @throws PersistException   если произошла ошибка при проверке
     * @throws WrongDataException если данные сущности неверны
     * @throws SQLException       если произошла ошибка SQL
     */
    protected void checkForCreate(T obj) throws PersistException, WrongDataException, SQLException {
        if (obj.getPK() != null) {
            if (getById(obj.getPK()) != null) {
                throw new WrongDataException("Item is already created");
            }
        }
    }

    /**
     * Парсит результат выполнения SQL-запроса в список сущностей.
     *
     * @param rs результат выполнения SQL-запроса
     * @return список сущностей
     * @throws SQLException если произошла ошибка при парсинге
     */
    protected abstract List<T> parseResultSet(ResultSet rs) throws SQLException;

    /**
     * Получает сущность по её первичному ключу.
     *
     * @param primaryKey первичный ключ сущности
     * @return сущность, соответствующую первичному ключу, или null, если сущность не найдена
     * @throws PersistException если произошла ошибка при выполнении запроса
     */
    @Override
    public T getById(PK primaryKey) throws PersistException {
        List<T> list;
        String sql = getSelectQuery();
        try (PreparedStatement statement = ConnectionHolder.getInstance().getConnection().prepareStatement(sql)) {
            prepareStatementForGetByPK(statement, primaryKey);
            ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs);
        } catch (Exception e) {
            throw new PersistException(e);
        }
        if (list == null || list.isEmpty()) {
            return null;
        }
        if (list.size() > 1) {
            throw new PersistException("Received more than one record.");
        }
        return list.iterator().next();
    }

    /**
     * Получает список всех сущностей.
     *
     * @param page номер страницы (не используется в этой реализации)
     * @return список всех сущностей
     * @throws PersistException если произошла ошибка при выполнении запроса
     */
    @Override
    public List<T> getAll(int page) throws PersistException {
        List<T> list;
        String sql = getSelectQuery();
        try (PreparedStatement statement = ConnectionHolder.getInstance().getConnection().prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs);

        } catch (Exception e) {
            throw new PersistException(e);
        }
        return list;
    }

    /**
     * Обновляет сущность в базе данных.
     *
     * @param obj обновляемая сущность
     * @throws PersistException   если произошла ошибка при выполнении запроса
     * @throws WrongDataException если данные сущности неверны
     * @throws SQLException       если произошла ошибка SQL
     */
    @Override
    public void update(T obj) throws PersistException, WrongDataException, SQLException {
        checkForUpdate(obj);
        String sql = getUpdateQuery();

        try (PreparedStatement statement = ConnectionHolder.getInstance().getConnection().prepareStatement(sql)) {
            prepareStatementForUpdate(statement, obj);
            int count = statement.executeUpdate();
            if (count != 1) {
                throw new PersistException("On update modify more then 1 record: " + count);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    /**
     * Удаляет сущность из базы данных по её первичному ключу.
     *
     * @param primaryKey первичный ключ удаляемой сущности
     * @throws PersistException   если произошла ошибка при удалении сущности
     * @throws WrongDataException если данные сущности неверны
     */
    @Override
    public void delete(PK primaryKey) throws PersistException, WrongDataException {
        checkForDelete(primaryKey);
        String sql = getDeleteQuery();
        try (PreparedStatement statement = ConnectionHolder.getInstance().getConnection().prepareStatement(sql)) {
            prepareStatementForDelete(statement, primaryKey);
            int count = statement.executeUpdate();
            if (count != 1) {
                throw new PersistException("On delete modify more then 1 record: " + count);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    /**
     * Создаёт новую сущность в базе данных.
     *
     * @param object создаваемая сущность
     * @throws PersistException   если произошла ошибка при создании сущности
     * @throws WrongDataException если данные сущности неверны
     * @throws SQLException       если произошла ошибка SQL
     */
    @Override
    public void create(T object) throws PersistException, WrongDataException, SQLException {
        checkForCreate(object);
        String sql = getCreateQuery();
        try (PreparedStatement statement = ConnectionHolder.getInstance().getConnection().prepareStatement(sql)) {
            prepareStatementForCreate(statement, object);
            int count = statement.executeUpdate();
            if (count != 1) {
                throw new PersistException("On create modify more then 1 record: " + count);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }
}
