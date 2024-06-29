package com.coworking_service.service.interfaces;

import com.coworking_service.exception.PersistException;

import java.time.LocalDate;

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
     * Выводит список рабочих пространств, отсортированных по указанной дате.
     *
     * @param date дата, по которой происходит сортировка рабочих пространств
     */
    void sortedByDate(LocalDate date);

    /**
     * Выводит список всех доступных рабочих пространств.
     */
    void getSpaces();
}
