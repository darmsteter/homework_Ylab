package com.coworking_service.service.interfaces;

import java.sql.Time;

/**
 * Интерфейс для управления временными интервалами (слотами).
 */
public interface SlotService {
    /**
     * Рассчитывает время начала бронирования для указанного номера слота.
     *
     * @param slotNumber номер слота
     * @return время начала бронирования в формате {@link Time}
     */
    Time calculateBookingTimeFrom(int slotNumber);

    /**
     * Рассчитывает время окончания бронирования для указанного номера слота и количества слотов.
     *
     * @param slotNumber    номер слота
     * @param numberOfSlots количество слотов
     * @return время окончания бронирования в формате {@link Time}
     */
    Time calculateBookingTimeTo(int slotNumber, int numberOfSlots);

}
