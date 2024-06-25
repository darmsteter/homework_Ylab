package com.coworking_service.model;

import com.coworking_service.util.Pair;

import java.time.LocalDate;

/**
 * Класс, предоставляющий временной интервал для бронирования.
 */
public class Slot {
    private final LocalDate date;
    private final Pair<String, Boolean>[] slots;

    /**
     * Конструктор для создания временного интервала.
     *
     * @param date дата бронирования
     * @param slots массив пар слотов времени и их доступности
     */
    public Slot(LocalDate date, Pair<String, Boolean>[] slots) {
        this.date = date;
        this.slots = slots;
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
}
