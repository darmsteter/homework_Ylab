package com.coworking_service.service;

import com.coworking_service.service.interfaces.SlotService;
import com.coworking_service.util.Pair;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Реализация интерфейса SlotService.
 */
public class SlotServiceImpl implements SlotService {
    private static final LocalTime START_TIME = LocalTime.of(8, 0);
    private static final LocalTime END_TIME = LocalTime.of(20, 0);
    private static final int SLOT_DURATION = 60;

    /**
     * Генерирует массив пар, представляющих слоты времени и их доступность.
     *
     * @return массив пар слотов времени и их доступности
     */
    @Override
    public Pair<String, Boolean>[] generateSlots(LocalDate date) {
        int totalSlots = (END_TIME.toSecondOfDay() - START_TIME.toSecondOfDay()) / (SLOT_DURATION * 60);
        Pair<String, Boolean>[] slotsArray = new Pair[totalSlots];
        LocalTime currentTime = START_TIME;

        for (int i = 0; i < totalSlots; i++) {
            String startTime = currentTime.format(DateTimeFormatter.ofPattern("HH:mm"));
            String endTime = currentTime.plusMinutes(SLOT_DURATION).format(DateTimeFormatter.ofPattern("HH:mm"));
            slotsArray[i] = new Pair<>((i + 1) + " слот: " + startTime + " - " + endTime, true);
            currentTime = currentTime.plusMinutes(SLOT_DURATION);
        }

        return slotsArray;
    }

    /**
     * Проверяет доступность слота по индексу.
     *
     * @param index индекс слота
     * @return true если слот доступен, false если занят
     */
    @Override
    public boolean isSlotAvailable(Pair<String, Boolean>[] slots, int index) {
        if (index < 0 || index >= slots.length) {
            throw new IllegalArgumentException("Invalid slot index");
        }
        return slots[index].value();
    }

    /**
     * Устанавливает доступность слота по индексу.
     *
     * @param index     индекс слота
     * @param available доступность слота (true если доступен, false если занят)
     */
    @Override
    public void setSlotAvailability(Pair<String, Boolean>[] slots, int index, boolean available) {
        if (index < 0 || index >= slots.length) {
            throw new IllegalArgumentException("Invalid slot index");
        }
        slots[index] = new Pair<>(slots[index].key(), available);
    }
}
