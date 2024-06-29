package com.coworking_service.model;

import com.coworking_service.service.SlotServiceImpl;
import com.coworking_service.util.Pair;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс, представляющий коворкинг-пространство.
 */
public class CoworkingSpace {
    private final HashMap<IndividualWorkplace, Map<String, Slot>> individualWorkplaces;
    private final HashMap<ConferenceRoom, Map<String, Slot>> conferenceRooms;

    private final SlotServiceImpl slotService = new SlotServiceImpl();

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

            if (slots.containsKey(date.toString())) {
                result.append(".Доступные слоты:\n");
                Slot slot = slots.get(date.toString());
                appendAvailableSlots(result, slot);
            } else {
                result.append("свободно весь день с 8 до 20. Слоты:\n");
                Slot newSlot = new Slot(date, slotService.generateSlots(date));
                slots.put(date.toString(), newSlot);
                appendAvailableSlots(result, newSlot);
            }
        }

        System.out.println(result);
    }

    /**
     * Проверяет и генерирует слоты для индивидуальных рабочих мест на указанную дату, если их нет.
     *
     * @param date дата, для которой необходимо проверить и создать слоты
     */
    public void checkAndGenerateSlotsForIndividualWorkplaces(LocalDate date) {
        for (Map<String, Slot> slots : individualWorkplaces.values()) {
            if (!slots.containsKey(date.toString())) {
                slots.put(date.toString(), new Slot(date, slotService.generateSlots(date)));
            }
        }
    }

    /**
     * Проверяет и генерирует слоты для конференц-залов на указанную дату, если их нет.
     *
     * @param date дата, для которой необходимо проверить и создать слоты
     */
    public void checkAndGenerateSlotsForConferenceRooms(LocalDate date) {
        for (Map<String, Slot> slots : conferenceRooms.values()) {
            if (!slots.containsKey(date.toString())) {
                slots.put(date.toString(), new Slot(date, slotService.generateSlots(date)));
            }
        }
    }

    /**
     * Добавляет доступные слоты в StringBuilder.
     *
     * @param result StringBuilder, в который добавляются слоты
     * @param slot   объект Slot, содержащий слоты времени
     */
    private void appendAvailableSlots(StringBuilder result, Slot slot) {
        for (Pair<String, Boolean> pair : slot.getSlots()) {
            if (pair.value()) {
                result.append(pair.key()).append("\n");
            }
        }
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

            if (slots.containsKey(date.toString())) {
                result.append(". Доступные слоты:\n");
                Slot slot = slots.get(date.toString());
                appendAvailableSlots(result, slot);
            } else {
                result.append("свободен весь день с 8 до 20. Слоты:\n");
                Slot newSlot = new Slot(date, slotService.generateSlots(date));
                slots.put(date.toString(), newSlot);
                appendAvailableSlots(result, newSlot);
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
            if (!slots.containsKey(date.toString())) {
                if (workplace instanceof IndividualWorkplace) {
                    checkAndGenerateSlotsForIndividualWorkplaces(date);
                } else if (workplace instanceof ConferenceRoom) {
                    checkAndGenerateSlotsForConferenceRooms(date);
                }
            }

            Slot slot = slots.get(date.toString());
            String[] reservedSlots = new String[numberOfSlots];

            for (int i = 0; i < numberOfSlots; i++) {
                int slotIndex = slotNumber - 1 + i;
                if (slotIndex < slot.getSlots().length) {
                    Pair<String, Boolean> pair = slot.getSlots()[slotIndex];
                    if (pair != null && pair.value()) {
                        slotService.setSlotAvailability(slot.getSlots(), slotIndex, false);
                        reservedSlots[i] = pair.key();
                    } else {
                        System.out.println("Слот " + (slotIndex + 1) + " уже занят или не существует.");
                        return null;
                    }
                } else {
                    System.out.println("Слот с номером " + (slotIndex + 1) + "не существует.");
                    return null;
                }
            }
            System.out.println("Слоты успешно зарезервированы.");
            return reservedSlots;
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
                        slotService.setSlotAvailability(currentSlot.getSlots(), index, availability);
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


