package com.coworking_service.repository.jdbc_repository;

import com.coworking_service.entity.WorkplaceEntity;
import com.coworking_service.exception.PersistException;
import com.coworking_service.util.ConnectionHolder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Репозиторий для работы с рабочими местами (Workplace) через JDBC.
 */
public class WorkplaceRepository extends JDBCRepository<WorkplaceEntity, Integer> {
    /**
     * Получает SQL-запрос SELECT для извлечения рабочего места по его первичному ключу (workplace_id).
     *
     * @return SQL-запрос SELECT.
     */
    @Override
    public String getSelectQuery() {
        return "SELECT * FROM coworking_service.\"Coworking\".workplace WHERE workplace_id = ?;";
    }

    /**
     * Получает SQL-запрос UPDATE для обновления информации о рабочем месте в базе данных.
     *
     * @return SQL-запрос UPDATE.
     */
    @Override
    public String getUpdateQuery() {
        return "UPDATE coworking_service.\"Coworking\".workplace SET maximum_capacity = ?, workplace_type = ? WHERE workplace_id = ?;";
    }

    /**
     * Получает SQL-запрос INSERT для создания нового рабочего места в базе данных.
     *
     * @return SQL-запрос INSERT.
     */
    @Override
    public String getCreateQuery() {
        return "INSERT INTO coworking_service.\"Coworking\".workplace (maximum_capacity, workplace_type) VALUES (?,?);";
    }

    /**
     * Получает SQL-запрос DELETE для удаления рабочего места из базы данных.
     *
     * @return SQL-запрос DELETE.
     */
    @Override
    public String getDeleteQuery() {
        return "DELETE FROM coworking_service.\"Coworking\".workplace WHERE workplace_id = ?;";
    }

    /**
     * Подготавливает PreparedStatement для выборки рабочего места по его первичному ключу.
     *
     * @param statement Подготавливаемый PreparedStatement.
     * @param primaryKey Первичный ключ рабочего места для выборки.
     * @throws SQLException Если происходит SQL-исключение при подготовке запроса.
     */
    @Override
    protected void prepareStatementForGetByPK(PreparedStatement statement, Integer primaryKey) throws SQLException {
        statement.setInt(1, primaryKey);
    }

    /**
     * Подготавливает PreparedStatement для обновления информации о рабочем месте.
     *
     * @param statement Подготавливаемый PreparedStatement.
     * @param obj Объект Workplace с обновленной информацией.
     * @throws SQLException Если происходит SQL-исключение при подготовке запроса.
     */
    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, WorkplaceEntity obj) throws SQLException {
        statement.setInt(1, obj.maximumCapacity());
        statement.setString(2, obj.workplaceType());
        statement.setInt(3, obj.getPK());
    }

    /**
     * Подготавливает PreparedStatement для создания нового рабочего места.
     *
     * @param statement Подготавливаемый PreparedStatement.
     * @param obj Объект Workplace с информацией о новом рабочем месте.
     * @throws SQLException Если происходит SQL-исключение при подготовке запроса.
     */
    @Override
    protected void prepareStatementForCreate(PreparedStatement statement, WorkplaceEntity obj) throws SQLException {
        statement.setInt(1, obj.maximumCapacity());
        statement.setString(2, obj.workplaceType());
    }

    /**
     * Подготавливает PreparedStatement для удаления рабочего места по его первичному ключу.
     *
     * @param statement Подготавливаемый PreparedStatement.
     * @param primaryKey Первичный ключ рабочего места для удаления.
     * @throws SQLException Если происходит SQL-исключение при подготовке запроса.
     */
    @Override
    protected void prepareStatementForDelete(PreparedStatement statement, Integer primaryKey) throws SQLException {
        statement.setInt(1, primaryKey);
    }

    /**
     * Получает список рабочих мест по их типу.
     *
     * @param type Тип рабочего места для выборки.
     * @return Список объектов Workplace, удовлетворяющих условию выборки.
     * @throws PersistException Если происходит ошибка при выполнении запроса к базе данных.
     */
    public List<WorkplaceEntity> getWorkplacesByType(String type) throws PersistException {
        List<WorkplaceEntity> list;
        String sql = "SELECT * FROM workplace WHERE workplace_type = ?;";
        try (PreparedStatement statement = ConnectionHolder.getInstance().getConnection().prepareStatement(sql)) {
            statement.setString(1, type);
            ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs);
        } catch (Exception e) {
            throw new PersistException(e);
        }
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list;
    }

    /**
     * Преобразует ResultSet в список объектов Workplace.
     *
     * @param rs ResultSet с результатами запроса.
     * @return Список объектов Workplace, полученных из ResultSet.
     * @throws SQLException Если происходит SQL-исключение при обработке ResultSet.
     */
    @Override
    protected List<WorkplaceEntity> parseResultSet(ResultSet rs) throws SQLException {
        List<WorkplaceEntity> workplaces = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("workplace_id");
            Integer maximumCapacity = rs.getInt("maximum_capacity");
            String workplaceType = rs.getString("workplace_type");
            WorkplaceEntity workplace = new WorkplaceEntity(id, maximumCapacity, workplaceType);
            workplaces.add(workplace);
        }
        return workplaces;
    }
}