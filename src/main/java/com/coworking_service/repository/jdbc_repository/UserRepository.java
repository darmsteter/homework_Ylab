package com.coworking_service.repository.jdbc_repository;

import com.coworking_service.entity.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * Репозиторий для работы с пользователями (Coworking_user) через JDBC.
 */
public class UserRepository extends JDBCRepository<User, Integer> {
    /**
     * Получает SQL-запрос SELECT для извлечения пользователя по его первичному ключу (id).
     *
     * @return SQL-запрос SELECT.
     */
    @Override
    public String getSelectQuery() {
        return "SELECT * FROM coworking_service.\"Coworking\".coworking_user where id = ?;";
    }

    /**
     * Получает SQL-запрос UPDATE для обновления информации о пользователе в базе данных.
     *
     * @return SQL-запрос UPDATE.
     */
    @Override
    public String getUpdateQuery() {
        return "UPDATE coworking_user SET login = ?, password = ?, role = ? WHERE id = ?;";
    }

    /**
     * Получает SQL-запрос INSERT для создания нового пользователя в базе данных.
     *
     * @return SQL-запрос INSERT.
     */
    @Override
    public String getCreateQuery() {
        return "INSERT INTO coworking_user (login, password, role) VALUES (?,?,?);";
    }

    /**
     * Получает SQL-запрос DELETE для удаления пользователя из базы данных.
     *
     * @return SQL-запрос DELETE.
     */
    @Override
    public String getDeleteQuery() {
        return "DELETE FROM coworking_user WHERE id = ?;";
    }

    /**
     * Подготавливает PreparedStatement для выборки пользователя по первичному ключу.
     *
     * @param statement Подготавливаемый PreparedStatement.
     * @param primaryKey Первичный ключ пользователя для выборки.
     * @throws SQLException Если происходит SQL-исключение при подготовке запроса.
     */
    @Override
    protected void prepareStatementForGetByPK(PreparedStatement statement, Integer primaryKey) throws SQLException {
        statement.setInt(1, primaryKey);
    }

    /**
     * Подготавливает PreparedStatement для обновления информации о пользователе.
     *
     * @param statement Подготавливаемый PreparedStatement.
     * @param obj Объект User с обновленной информацией.
     * @throws SQLException Если происходит SQL-исключение при подготовке запроса.
     */
    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, User obj) throws SQLException {
        statement.setString(1, obj.login());
        statement.setString(2, obj.password());
        statement.setString(3, obj.role());
        statement.setInt(4, obj.getPK());
    }

    /**
     * Подготавливает PreparedStatement для создания нового пользователя.
     *
     * @param statement Подготавливаемый PreparedStatement.
     * @param obj Объект User с информацией о новом пользователе.
     * @throws SQLException Если происходит SQL-исключение при подготовке запроса.
     */
    @Override
    protected void prepareStatementForCreate(PreparedStatement statement, User obj) throws SQLException {
        statement.setString(1, obj.login());
        statement.setString(2, obj.password());
        statement.setString(3, obj.role().toString());
    }

    /**
     * Подготавливает PreparedStatement для удаления пользователя по первичному ключу.
     *
     * @param statement Подготавливаемый PreparedStatement.
     * @param primaryKey Первичный ключ пользователя для удаления.
     * @throws SQLException Если происходит SQL-исключение при подготовке запроса.
     */
    @Override
    protected void prepareStatementForDelete(PreparedStatement statement, Integer primaryKey) throws SQLException {
        statement.setInt(1, primaryKey);
    }

    /**
     * Преобразует ResultSet в список объектов User.
     *
     * @param rs ResultSet с результатами запроса.
     * @return Список объектов User, полученных из ResultSet.
     * @throws SQLException Если происходит SQL-исключение при обработке ResultSet.
     */
    @Override
    protected List<User> parseResultSet(ResultSet rs) throws SQLException {
        List<User> users = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String login = rs.getString("login");
            String password = rs.getString("password");
            String role = rs.getString("role");
            User user = new User(id, login, password, role);
            users.add(user);
        }
        return users;
    }
}
