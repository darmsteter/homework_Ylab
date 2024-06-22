package com.coworking_service.model;

import java.time.LocalDate;

/**
 * Класс, представляющий информацию о бронировании рабочего места или конференц-зала.
 */
public record Booking(String userLogin, int workplaceID, LocalDate bookingDate, String[] slots) {
}
