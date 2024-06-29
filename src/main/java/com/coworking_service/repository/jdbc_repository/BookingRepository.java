package com.coworking_service.repository.jdbc_repository;

import com.coworking_service.entity.Booking;
import com.coworking_service.exception.PersistException;
import com.coworking_service.util.ConnectionHolder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Репозиторий для работы с бронированиями (Booking) через JDBC.
 */
public class BookingRepository extends JDBCRepository<Booking, Integer> {
    /**
     * Возвращает SQL-запрос для выборки бронирования по его идентификатору.
     *
     * @return SQL-запрос для выборки бронирования по идентификатору
     */
    @Override
    public String getSelectQuery() {
        return "SELECT * FROM booking WHERE booking_id = ?;";
    }

    /**
     * Возвращает SQL-запрос для обновления данных бронирования.
     *
     * @return SQL-запрос для обновления данных бронирования
     */
    @Override
    public String getUpdateQuery() {
        return "UPDATE booking SET user_id = ?, " +
                "workplace_id = ?, " +
                "booking_date = ?, " +
                "booking_time_from = ?, " +
                "booking_time_to = ? " +
                "WHERE booking_id = ?;";
    }

    /**
     * Возвращает SQL-запрос для создания нового бронирования.
     *
     * @return SQL-запрос для создания нового бронирования
     */
    @Override
    public String getCreateQuery() {
        return "INSERT INTO booking (user_id, workplace_id, booking_date, booking_time_from, booking_time_to) " +
                "VALUES (?,?,?,?,?);";
    }

    /**
     * Возвращает SQL-запрос для удаления бронирования по его идентификатору.
     *
     * @return SQL-запрос для удаления бронирования по идентификатору
     */
    @Override
    public String getDeleteQuery() {
        return "DELETE FROM booking WHERE booking_id = ?;";
    }

    /**
     * Подготавливает SQL-выражение для выборки бронирования по его идентификатору.
     *
     * @param statement  подготовленное SQL-выражение
     * @param primaryKey идентификатор бронирования
     * @throws SQLException если произошла ошибка при подготовке выражения
     */
    @Override
    protected void prepareStatementForGetByPK(PreparedStatement statement, Integer primaryKey) throws SQLException {
        statement.setInt(1, primaryKey);
    }

    /**
     * Подготавливает SQL-выражение для обновления данных бронирования.
     *
     * @param statement подготовленное SQL-выражение
     * @param obj       обновляемое бронирование
     * @throws SQLException если произошла ошибка при подготовке выражения
     */
    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Booking obj) throws SQLException {
        statement.setInt(1, obj.userId());
        statement.setInt(2, obj.workplaceId());
        statement.setDate(3, obj.bookingDate());
        statement.setTime(4, obj.bookingTimeFrom());
        statement.setTime(5, obj.bookingTimeTo());
        statement.setInt(6, obj.getPK());
    }

    /**
     * Подготавливает SQL-выражение для создания нового бронирования.
     *
     * @param statement подготовленное SQL-выражение
     * @param obj       создаваемое бронирование
     * @throws SQLException если произошла ошибка при подготовке выражения
     */
    @Override
    protected void prepareStatementForCreate(PreparedStatement statement, Booking obj) throws SQLException {
        statement.setInt(1, obj.userId());
        statement.setInt(2, obj.workplaceId());
        statement.setDate(3, obj.bookingDate());
        statement.setTime(4, obj.bookingTimeFrom());
        statement.setTime(5, obj.bookingTimeTo());
    }

    /**
     * Подготавливает SQL-выражение для удаления бронирования по его идентификатору.
     *
     * @param statement  подготовленное SQL-выражение
     * @param primaryKey идентификатор бронирования
     * @throws SQLException если произошла ошибка при подготовке выражения
     */
    @Override
    protected void prepareStatementForDelete(PreparedStatement statement, Integer primaryKey) throws SQLException {
        statement.setInt(1, primaryKey);
    }

    /**
     * Возвращает список бронирований по указанной дате и идентификатору пользователя.
     *
     * @param date   дата бронирования
     * @param userId идентификатор пользователя
     * @return список бронирований, соответствующих заданным параметрам
     * @throws PersistException если произошла ошибка при выполнении запроса
     */
    public List<Booking> getBookingsByDateAndUser(Date date, Integer userId) throws PersistException {
        if (date == null && userId == null)
            return null;
        List<Booking> list;
        String sql = "SELECT * FROM booking WHERE";
        boolean is_date = false;
        if (date != null) {
            sql += " booking_date = ?";
            is_date = true;
        }
        if (userId != null) {
            if (is_date)
                sql += " AND";
            sql += " user_id = ?";
        }
        sql += ";";
        try (PreparedStatement statement = ConnectionHolder.getInstance().getConnection().prepareStatement(sql)) {
            int index = 1;
            if (date != null) {
                statement.setDate(index, date);
                ++index;
            }
            if (userId != null)
                statement.setInt(index, userId);
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
     * Парсит результат выполнения SQL-запроса в список бронирований.
     *
     * @param rs результат выполнения SQL-запроса
     * @return список бронирований
     * @throws SQLException если произошла ошибка при парсинге
     */
    @Override
    protected List<Booking> parseResultSet(ResultSet rs) throws SQLException {
        List<Booking> bookings = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("booking_id");
            Integer userId = rs.getInt("user_id");
            Integer workplaceId = rs.getInt("workplace_id");
            Date bookingDate = rs.getDate("booking_date");
            Time bookingTimeFrom = rs.getTime("booking_time_from");
            Time bookingTimeTo = rs.getTime("booking_time_to");
            Booking booking = new Booking(id, userId, workplaceId, bookingDate, bookingTimeFrom, bookingTimeTo);
            bookings.add(booking);
        }
        return bookings;
    }
}