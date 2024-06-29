package com.coworking_service.service;

import com.coworking_service.entity.Workplace;
import com.coworking_service.exception.PersistException;
import com.coworking_service.exception.WrongDataException;
import com.coworking_service.repository.WorkplaceRepository;
import com.coworking_service.service.interfaces.CoworkingSpaceService;

import java.sql.SQLException;
import java.util.List;

/**
 * Реализация сервиса управления рабочими пространствами.
 * Этот класс использует объект CoworkingSpace для выполнения операций над индивидуальными рабочими местами и конференц-залами.
 */
public class CoworkingSpaceServiceImpl implements CoworkingSpaceService {
    private final WorkplaceRepository workplaceRepository = new WorkplaceRepository();

    /**
     * Добавляет новое индивидуальное рабочее место в коворкинге.
     */
    public void addIndividualWorkplaceToDatabase() {
        try {
            Workplace workplace = new Workplace(null, 1, "individual");
            workplaceRepository.create(workplace);
        } catch (PersistException e) {
            System.err.println("Ошибка при создании индивидуального рабочего места: " + e.getMessage());
        } catch (WrongDataException e) {
            System.err.println("Ошибка при создании индивидуального рабочего места из-за некорректных данных: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Ошибка SQL при создании индивидуального рабочего места: " + e.getMessage());
        }
    }
    /**
     * Добавляет новый конференц-зал с указанной вместимостью в коворкинге.
     *
     * @param maxCapacity максимальная вместимость конференц-зала
     */
    public void addConferenceRoomToDatabase(int maxCapacity) {
        try {
            Workplace workplace = new Workplace(null, maxCapacity, "conference");
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
     * Выводит список всех рабочих пространств в CoworkingSpace.
     *
     * @throws PersistException если возникает ошибка при работе с базой данных
     */
    public void getSpaces() throws PersistException {
        List<Workplace> individualWorkplaces = workplaceRepository.getWorkplacesByType("individual");
        List<Workplace> conferenceRooms = workplaceRepository.getWorkplacesByType("conference");

        System.out.println("Индивидуальные рабочие места");
        for (Workplace workplace : individualWorkplaces) {
            System.out.println("ИРМ # " + workplace.getPK());
        }

        System.out.println("\nКонференц-залы:");
        for (Workplace room : conferenceRooms) {
            System.out.println("КЗ # " + room.getPK() + ", Максимальная вместимость: " + room.getMaximumCapacity());
        }
    }
}
