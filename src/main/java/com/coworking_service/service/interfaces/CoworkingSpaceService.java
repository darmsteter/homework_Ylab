package com.coworking_service.service.interfaces;

import com.coworking_service.entity.Booking;
import com.coworking_service.exception.PersistException;

import java.time.LocalDate;
import java.util.List;

/**
 * Интерфейс для управления рабочими пространствами в коворкинге.
 */
public interface CoworkingSpaceService {
    /**
     * Добавляет новое индивидуальное рабочее место в коворкинге.
     */
    void addIndividualWorkplaceToDatabase() throws PersistException;

    /**
     * Добавляет новый конференц-зал с указанной вместимостью в коворкинге.
     *
     * @param maxCapacity максимальная вместимость конференц-зала
     */
    void addConferenceRoomToDatabase(int maxCapacity) throws PersistException;

    /**
     * Выводит список всех доступных рабочих пространств.
     */
    void getSpaces() throws PersistException;

    public List<Booking> sortedByDate(LocalDate date) throws PersistException;
}
