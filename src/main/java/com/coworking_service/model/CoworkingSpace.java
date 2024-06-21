package com.coworking_service.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс, представляющий коворкинг-пространство.
 */
public class CoworkingSpace {
    private final List<IndividualWorkplace> individualWorkplaces;
    private final List<ConferenceRoom> conferenceRooms;

    /**
     * Конструктор для создания коворкинг-пространства.
     */
    public CoworkingSpace() {
        this.individualWorkplaces = new ArrayList<>();
        this.conferenceRooms = new ArrayList<>();
    }

    /**
     * Добавляет новое индивидуальное рабочее место.
     *
     * @param workplaceID номер рабочего места
     */
    public void addIndividualWorkplace(int workplaceID) {
        individualWorkplaces.add(new IndividualWorkplace(workplaceID));
    }

    /**
     * Добавляет новый конференц-зал.
     *
     * @param workplaceID номер конференц-зала.
     * @param maximumCapacity максимальная вместимость конференц-зала
     */
    public void addConferenceRoom(int workplaceID, int maximumCapacity) {
        conferenceRooms.add(new ConferenceRoom(workplaceID, maximumCapacity));
    }

    /**
     * Получает список индивидуальных рабочих мест в коворкинг-пространстве.
     *
     * @return список индивидуальных рабочих мест
     */
    public List<IndividualWorkplace> getIndividualWorkplaces() {
        return individualWorkplaces;
    }

    /**
     * Получает список конференц-залов в коворкинг-пространстве.
     *
     * @return список конференц-залов
     */
    public List<ConferenceRoom> getConferenceRooms() {
        return conferenceRooms;
    }
}


