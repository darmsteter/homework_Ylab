package com.coworking_service.service.interfaces;

import com.coworking_service.entity.User;
import com.coworking_service.exception.PersistException;
import com.coworking_service.exception.WrongDataException;

import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Интерфейс для управления бронями.
 */
public interface BookingService {
    /**
     * Создает новую бронь для пользователя.
     *
     * @param onlineUser    объект пользователя
     * @param date          дата бронирования
     * @param workplaceID   идентификатор рабочего пространства
     * @param slotNumber    номер первого бронируемого слота
     * @param numberOfSlots количество бронируемых слотов
     * @throws PersistException   если произошла ошибка при доступе к базе данных
     * @throws SQLException       если произошла ошибка при выполнении SQL-запроса
     * @throws WrongDataException если введены некорректные данные
     */
    void createNewBooking(User onlineUser, LocalDate date, int workplaceID, int slotNumber, int numberOfSlots)
            throws PersistException, SQLException, WrongDataException;

    /**
     * Удаляет бронирование пользователя по логину, дате и выбранному номеру бронирования.
     *
     * @param onlineUser объект пользователя, выполняющего операцию
     * @param userLogin  логин пользователя, чью бронь нужно удалить
     * @param dateInput  дата бронирования в формате "yyyy-MM-dd"
     * @param choice     номер выбранного бронирования для удаления
     * @throws PersistException   если произошла ошибка при доступе к базе данных
     * @throws WrongDataException если введены некорректные данные
     */
    void deleteBooking(User onlineUser, String userLogin, String dateInput, int choice) throws WrongDataException, PersistException;
}
