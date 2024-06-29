package com.coworking_service.service.interfaces;

import com.coworking_service.model.User;

import java.time.LocalDate;

/**
 * Интерфейс для управления бронированием рабочих пространств.
 */
public interface BookingService {
    /**
     * Создает новое бронирование для указанного пользователя на заданную дату.
     *
     * @param onlineUser   объект пользователя, который создает бронь
     * @param date         дата бронирования
     * @param answer       тип рабочего пространства (например, "I" - индивидуальное рабочее место, "C" - конференц-зал)
     * @param workplaceID  идентификатор рабочего пространства
     * @param slotNumber   номер первого бронируемого слота
     * @param numberOfSlots количество бронируемых слотов
     */
    void createNewBooking(
            User onlineUser,
            LocalDate date,
            String answer,
            int workplaceID,
            int slotNumber,
            int numberOfSlots
    );

    /**
     * Удаляет бронирование пользователя по его логину и дате.
     *
     * @param userLogin логин пользователя, чье бронирование нужно удалить
     * @param date      дата бронирования, которую нужно удалить
     */
    void deleteBooking(String userLogin, LocalDate date);
}
