package com.coworking_service.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Класс, предоставляющий временной интервал для бронирования.
 */
public class Slot {
    private final LocalDate date;
    private final String[] slots;

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
     * Генерирует массив строк, представляющих слоты времени.
     *
     * @return массив строк слотов времени
     */
    private String[] generateSlots() {
        int totalSlots = (END_TIME.toSecondOfDay() - START_TIME.toSecondOfDay()) / (SLOT_DURATION * 60);
        String[] slotsArray = new String[totalSlots];
        LocalTime currentTime = START_TIME;

        for (int i = 0; i < totalSlots; i++) {
            String startTime = currentTime.format(DateTimeFormatter.ofPattern("HH:mm"));
            String endTime = currentTime.plusMinutes(SLOT_DURATION).format(DateTimeFormatter.ofPattern("HH:mm"));
            slotsArray[i] = (i + 1) + " слот: " + startTime + " - " + endTime;
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
     * Получает массив временных интервалов.
     *
     * @return массив временных интервалов
     */
    public String[] getSlots() {
        return slots;
    }
}
