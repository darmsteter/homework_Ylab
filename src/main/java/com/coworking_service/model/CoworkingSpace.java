package com.coworking_service.model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс, представляющий коворкинг-пространство.
 */
public class CoworkingSpace {
    private final HashMap<IndividualWorkplace, Map<String, Slot>> individualWorkplaces;
    private final HashMap<ConferenceRoom, Map<String, Slot>> conferenceRooms;

    /**
     * Конструктор для создания коворкинг-пространства.
     */
    public CoworkingSpace() {
        this.individualWorkplaces = new HashMap<>();
        this.conferenceRooms = new HashMap<>();
    }

    /**
     * Добавляет новое индивидуальное рабочее место.
     */
    public void addIndividualWorkplace() {
        individualWorkplaces.put(new IndividualWorkplace(individualWorkplaces.size()), new HashMap<>());
    }

    /**
     * Добавляет новый конференц-зал.
     *
     * @param maximumCapacity максимальная вместимость конференц-зала
     */
    public void addConferenceRoom(int maximumCapacity) {
        conferenceRooms.put(new ConferenceRoom(conferenceRooms.size(), maximumCapacity), new HashMap<>());
    }

    /**
     * Возвращает все индивидуальные рабочие места с их слотами на указанную дату.
     *
     * @param date дата, для которой необходимо получить слоты
     */
    public void getIndividualWorkplacesSlotsByDate(LocalDate date) {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<IndividualWorkplace, Map<String, Slot>> entry : individualWorkplaces.entrySet()) {
            result.append("Рабочее место #").append(entry.getKey().getWorkplaceID()).append(" ");
            Map<String, Slot> slots = entry.getValue();
            if (slots.containsKey(date)) {
                result.append("занято. Доступные слоты:\n");
                Slot slot = slots.get(date);
                for (Pair<String, Boolean> pair : slot.getSlots()) {
                    if (pair.getValue()) {
                        result.append(pair.getKey()).append("\n");
                    }
                }
            } else {
                result.append("свободно весь день с 8 до 20.\n");
                slots.put(date.toString(), new Slot(date));
            }
        }
        System.out.println(result);
    }

    /**
     * Возвращает все конференц-залы с их слотами на указанную дату.
     *
     * @param date дата, для которой необходимо получить слоты
     */
    public void getConferenceRoomsSlotsByDate(LocalDate date) {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<ConferenceRoom, Map<String, Slot>> entry : conferenceRooms.entrySet()) {
            result.append("Конференц-зал #").append(entry.getKey().getWorkplaceID()).append(" ");
            Map<String, Slot> slots = entry.getValue();
            if (slots.containsKey(date)) {
                result.append("занят. Доступные слоты:\n");
                Slot slot = slots.get(date);
                for (Pair<String, Boolean> pair : slot.getSlots()) {
                    if (pair.getValue()) {
                        result.append(pair.getKey()).append("\n");
                    }
                }
            } else {
                result.append("свободен весь день с 8 до 20.\n");
                slots.put(date.toString(), new Slot(date));
            }
        }
        System.out.println(result);
    }

    /**
     * Получает список индивидуальных рабочих мест в коворкинг-пространстве.
     *
     * @return список индивидуальных рабочих мест
     */
    public HashMap<IndividualWorkplace, Map<String, Slot>> getIndividualWorkplaces() {
        return individualWorkplaces;
    }

    /**
     * Получает список конференц-залов в коворкинг-пространстве.
     *
     * @return список конференц-залов
     */
    public HashMap<ConferenceRoom, Map<String, Slot>> getConferenceRooms() {
        return conferenceRooms;
    }

    /**
     * Печатает строку с перечислением всех индивидуальных мест и конференц-залов с максимальной вместимостью.
     */
    public void printSpaces() {
        StringBuilder result = new StringBuilder();

        result.append("Индивидуальные рабочие места:\n");
        for (Map.Entry<IndividualWorkplace, Map<String, Slot>> entry : individualWorkplaces.entrySet()) {
            IndividualWorkplace workplace = entry.getKey();
            result.append("Рабочее место #").append(workplace.getWorkplaceID()).append("\n");
        }

        result.append("Конференц-залы:\n");
        for (Map.Entry<ConferenceRoom, Map<String, Slot>> entry : conferenceRooms.entrySet()) {
            ConferenceRoom room = entry.getKey();
            result.append("Конференц-зал #").append(room.getWorkplaceID())
                    .append(", максимальная вместимость: ").append(room.getMaximumCapacity()).append("\n");
        }

        System.out.println(result);
    }
}


