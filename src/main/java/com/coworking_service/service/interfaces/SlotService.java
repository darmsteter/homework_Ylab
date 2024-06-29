package com.coworking_service.service.interfaces;

import java.sql.Time;

/**
 * Интерфейс для управления временными интервалами (слотами).
 */
public interface SlotService {

    Time calculateBookingTimeFrom(int slotNumber);
    Time calculateBookingTimeTo(int slotNumber, int numberOfSlots);

}
