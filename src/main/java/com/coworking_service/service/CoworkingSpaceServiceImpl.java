package com.coworking_service.service;

import com.coworking_service.model.CoworkingSpace;
import com.coworking_service.service.interfaces.CoworkingSpaceService;

import java.time.LocalDate;

/**
 * Реализация сервиса управления рабочими пространствами.
 * Этот класс использует объект CoworkingSpace для выполнения операций над индивидуальными рабочими местами и конференц-залами.
 */
public record CoworkingSpaceServiceImpl(CoworkingSpace coworkingSpace) implements CoworkingSpaceService {
    /**
     * Добавляет новое индивидуальное рабочее место в CoworkingSpace.
     */
    public void addIndividualWorkplace() {
        coworkingSpace.addIndividualWorkplace();
    }

    /**
     * Добавляет новый конференц-зал в CoworkingSpace с указанной максимальной вместимостью.
     *
     * @param maxCapacity максимальная вместимость конференц-зала
     */
    public void addConferenceRoom(int maxCapacity) {
        coworkingSpace.addConferenceRoom(maxCapacity);
    }

    /**
     * Выводит информацию о занятости индивидуальных рабочих мест и конференц-залов на указанную дату.
     *
     * @param date дата, для которой требуется вывести информацию о занятости
     */
    public void sortedByDate(LocalDate date) {
        coworkingSpace.getIndividualWorkplacesSlotsByDate(date);
        coworkingSpace.getConferenceRoomsSlotsByDate(date);
    }

    /**
     * Выводит список всех рабочих пространств в CoworkingSpace.
     */
    public void getSpaces() {
        coworkingSpace.printSpaces();
    }
}
