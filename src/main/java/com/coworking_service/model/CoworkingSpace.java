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
                    if (pair.value()) {
                        result.append(pair.key()).append("\n");
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
                    if (pair.value()) {
                        result.append(pair.key()).append("\n");
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
     * Печатает строку с перечислением всех индивидуальных мест и конференц-залов с их максимальной вместимостью.
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

    /**
     * Ищет индивидуальное рабочее место по его ID.
     *
     * @param workplaceID ID индивидуального рабочего места для поиска
     * @return найденное индивидуальное рабочее место или null, если не найдено
     */
    public IndividualWorkplace findIndividualWorkplaceById(int workplaceID) {
        for (IndividualWorkplace workplace : individualWorkplaces.keySet()) {
            if (workplace.getWorkplaceID() == workplaceID) {
                return workplace;
            }
        }
        return null;
    }

    /**
     * Ищет конференц-зал по его ID.
     *
     * @param workplaceID ID конференц-зала для поиска
     * @return найденный конференц-зал или null, если не найден
     */
    public ConferenceRoom findConferenceRoomById(int workplaceID) {
        for (ConferenceRoom room : conferenceRooms.keySet()) {
            if (room.getWorkplaceID() == workplaceID) {
                return room;
            }
        }
        return null;
    }

    /**
     * Резервирует слоты для рабочего места или конференц-зала на указанную дату.
     *
     * @param workplace     объект рабочего места или конференц-зала
     * @param slots         карта слотов для рабочего места или конференц-зала
     * @param workplaceID   ID рабочего места или конференц-зала
     * @param date          дата для бронирования слотов
     * @param slotNumber    начальный номер слота для бронирования
     * @param numberOfSlots количество слотов для бронирования
     * @return массив зарезервированных слотов или null, если бронирование не удалось
     */
    public String[] reserveSlots(Workplace workplace, Map<String, Slot> slots, int workplaceID, LocalDate date, int slotNumber, int numberOfSlots) {
        if (workplace.getWorkplaceID() == workplaceID) {
            if (slots.containsKey(date.toString())) {
                Slot slot = slots.get(date.toString());
                String[] reservedSlots = new String[numberOfSlots];

                for (int i = 0; i < numberOfSlots; i++) {
                    int slotIndex = slotNumber - 1 + i;
                    if (slotIndex < slot.getSlots().length) {
                        Pair<String, Boolean> pair = slot.getSlots()[slotIndex];
                        if (pair != null && pair.value()) {
                            slot.setSlotAvailability(slotIndex, false);
                            reservedSlots[i] = pair.key();
                        } else {
                            System.out.println("Слот " + (slotIndex + 1) + " уже занят или не существует.");
                            return null;
                        }
                    } else {
                        System.out.println("Слот с номером " + (slotIndex + 1) + " не существует.");
                        return null;
                    }
                }
                System.out.println("Слоты успешно зарезервированы.");
                return reservedSlots;
            } else {
                System.out.println("Для указанной даты " + date + " нет зарегистрированных слотов.");
                return null;
            }
        } else {
            System.out.println("Рабочее место или конференц-зал с ID " + workplaceID + " не найдено.");
            return null;
        }
    }

    /**
     * Устанавливает доступность указанных слотов для рабочего места или конференц-зала.
     *
     * @param workplace    объект рабочего места или конференц-зала
     * @param slots        массив имен слотов, доступность которых необходимо изменить
     * @param availability новое значение доступности для указанных слотов
     */
    public void setSlotsAvailability(Workplace workplace, String[] slots, boolean availability) {
        Map<String, Slot> slotsMap = (workplace instanceof IndividualWorkplace) ?
                individualWorkplaces.get(workplace) :
                conferenceRooms.get(workplace);

        if (slotsMap != null) {
            for (Map.Entry<String, Slot> entry : slotsMap.entrySet()) {
                Slot currentSlot = entry.getValue();
                for (String slotName : slots) {
                    int index = getSlotIndex(currentSlot, slotName);
                    if (index >= 0) {
                        currentSlot.setSlotAvailability(index, availability);
                    }
                }
            }
        }
    }


    /**
     * Возвращает индекс слота в массиве слотов.
     *
     * @param currentSlot текущий слот
     * @param slotName    имя слота
     * @return индекс слота
     */
    private int getSlotIndex(Slot currentSlot, String slotName) {
        Pair<String, Boolean>[] slotsArray = currentSlot.getSlots();
        for (int i = 0; i < slotsArray.length; i++) {
            if (slotsArray[i].key().equals(slotName)) {
                return i;
            }
        }
        throw new IllegalArgumentException("Invalid slot name: " + slotName);
    }

}


