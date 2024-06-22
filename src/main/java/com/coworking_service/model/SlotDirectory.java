package com.coworking_service.model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс для управления коллекцией слотов.
 */
public class SlotDirectory {
    private final Map<LocalDate, Slot> slotsMap;

    public SlotDirectory() {
        this.slotsMap = new HashMap<>();
    }

    /**
     * Создает и добавляет слот в коллекцию.
     *
     * @param date дата для создания слота
     * @return созданный слот
     */
    public Slot createSlot(LocalDate date) {
        Slot slot = new Slot(date);
        slotsMap.put(date, slot);
        return slot;
    }

    /**
     * Находит слот по дате.
     *
     * @param date дата для поиска
     * @return найденный слот или null, если слот не найден
     */
    public Slot findSlotByDate(LocalDate date) {
        return slotsMap.get(date);
    }
}
