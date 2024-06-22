package com.coworking_service.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Класс, предоставляющий временной интервал для бронирования.
 */
public class Slot {
    private final LocalDate date;
    private final Pair<String, Boolean>[] slots;

    private static final LocalTime START_TIME = LocalTime.of(8, 0);
    private static final LocalTime END_TIME = LocalTime.of(20, 0);
    private static final int SLOT_DURATION = 60;

    /**
     * Конструктор для создания временного интервала.
     *
     * @param date дата бронирования
     */
    public Slot(LocalDate date) {
        this.date = date;
        slots = generateSlots();
    }

    /**
     * Генерирует массив пар, представляющих слоты времени и их доступность.
     *
     * @return массив пар слотов времени и их доступности
     */
    private Pair<String, Boolean>[] generateSlots() {
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
     * Получает дату, которой принадлежит временной интервал.
     *
     * @return дата бронирования
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Получает массив временных интервалов и их доступности.
     *
     * @return массив пар временных интервалов и их доступности
     */
    public Pair<String, Boolean>[] getSlots() {
        return slots;
    }

    /**
     * Проверяет доступность слота по индексу.
     *
     * @param index индекс слота
     * @return true если слот доступен, false если занят
     */
    public boolean isSlotAvailable(int index) {
        if (index < 0 || index >= slots.length) {
            throw new IllegalArgumentException("Invalid slot index");
        }
        return slots[index].getValue();
    }

    /**
     * Устанавливает доступность слота по индексу.
     *
     * @param index     индекс слота
     * @param available доступность слота (true если доступен, false если занят)
     */
    public void setSlotAvailability(int index, boolean available) {
        if (index < 0 || index >= slots.length) {
            throw new IllegalArgumentException("Invalid slot index");
        }
        slots[index] = new Pair<>(slots[index].getKey(), available);
    }
}
