package com.coworking_service.service.interfaces;

import com.coworking_service.util.Pair;

import java.sql.Time;
import java.time.LocalDate;

/**
 * Интерфейс для управления временными интервалами (слотами).
 */
public interface SlotService {

    /**
     * Генерирует массив пар, представляющих слоты времени и их доступность.
     *
     * @param date дата бронирования
     * @return массив пар слотов времени и их доступности
     */
    Pair<String, Boolean>[] generateSlots(LocalDate date);

    /**
     * Проверяет доступность слота по индексу.
     *
     * @param slots массив слотов
     * @param index индекс слота
     * @return true если слот доступен, false если занят
     */
    boolean isSlotAvailable(Pair<String, Boolean>[] slots, int index);

    /**
     * Устанавливает доступность слота по индексу.
     *
     * @param slots массив слотов
     * @param index индекс слота
     * @param available доступность слота (true если доступен, false если занят)
     */
    void setSlotAvailability(Pair<String, Boolean>[] slots, int index, boolean available);

    Time calculateBookingTimeFrom(int slotNumber);
    Time calculateBookingTimeTo(int slotNumber, int numberOfSlots);

}
