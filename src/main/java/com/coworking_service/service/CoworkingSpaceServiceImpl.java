package com.coworking_service.service;

import com.coworking_service.entity.WorkplaceEntity;
import com.coworking_service.exception.PersistException;
import com.coworking_service.exception.WrongDataException;
import com.coworking_service.model.CoworkingSpace;
import com.coworking_service.repository.jdbc_repository.WorkplaceRepository;
import com.coworking_service.service.interfaces.CoworkingSpaceService;

import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Реализация сервиса управления рабочими пространствами.
 * Этот класс использует объект CoworkingSpace для выполнения операций над индивидуальными рабочими местами и конференц-залами.
 */
public record CoworkingSpaceServiceImpl(CoworkingSpace coworkingSpace) implements CoworkingSpaceService {
    private static final WorkplaceRepository workplaceRepository = new WorkplaceRepository();

    public void addIndividualWorkplaceToDatabase() {
        try {
            WorkplaceEntity workplace = new WorkplaceEntity(null, 1, "individual");
            workplaceRepository.create(workplace);
        } catch (PersistException e) {
            System.err.println("Ошибка при создании индивидуального рабочего места: " + e.getMessage());
        } catch (WrongDataException e) {
            System.err.println("Ошибка при создании индивидуального рабочего места из-за некорректных данных: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Ошибка SQL при создании индивидуального рабочего места: " + e.getMessage());
        }
    }

    public void addConferenceRoomToDatabase(int maxCapacity) {
        try {
            WorkplaceEntity workplace = new WorkplaceEntity(null, maxCapacity, "conference");
            workplaceRepository.create(workplace);
        } catch (PersistException e) {
            System.err.println("Ошибка при создании конференц-зала: " + e.getMessage());
        } catch (WrongDataException e) {
            System.err.println("Ошибка при создании конференц-зала из-за некорректных данных: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Ошибка SQL при создании конференц-зала: " + e.getMessage());
        }
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