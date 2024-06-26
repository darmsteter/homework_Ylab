package com.coworking_service.service.interfaces;

import java.time.LocalDate;

/**
 * Интерфейс для управления рабочими пространствами в коворкинге.
 */
public interface CoworkingSpaceService {
    /**
     * Добавляет новое индивидуальное рабочее место в коворкинге.
     */
    void addIndividualWorkplace();

    /**
     * Добавляет новый конференц-зал с указанной вместимостью в коворкинге.
     *
     * @param maxCapacity максимальная вместимость конференц-зала
     */
    void addConferenceRoom(int maxCapacity);

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
