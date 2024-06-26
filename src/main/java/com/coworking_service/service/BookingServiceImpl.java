package com.coworking_service.service;

import com.coworking_service.model.*;
import com.coworking_service.repository.BookingDirectory;
import com.coworking_service.service.interfaces.BookingService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Реализация сервиса бронирования рабочих мест и конференц-залов.
 * Обеспечивает создание, удаление и получение информации о бронированиях.
 */
public class BookingServiceImpl implements BookingService {
    private final CoworkingSpace coworkingSpace;
    private final BookingDirectory bookingDirectory;

    /**
     * Конструктор для создания экземпляра сервиса бронирования.
     *
     * @param coworkingSpace   рабочее пространство
     * @param bookingDirectory репозиторий для хранения бронирований
     */
    public BookingServiceImpl(CoworkingSpace coworkingSpace, BookingDirectory bookingDirectory) {
        this.coworkingSpace = coworkingSpace;
        this.bookingDirectory = bookingDirectory;
    }

    /**
     * Создает новое бронирование для пользователя.
     *
     * @param onlineUser    пользователь, создающий бронь
     * @param date          дата бронирования
     * @param answer        тип рабочего места ("I" для индивидуального рабочего места, "C" для конференц-зала)
     * @param workplaceID   идентификатор рабочего места
     * @param slotNumber    номер начального слота
     * @param numberOfSlots количество бронируемых слотов
     */
    public void createNewBooking(User onlineUser, LocalDate date, String answer, int workplaceID, int slotNumber, int numberOfSlots) {
        Workplace workplace;
        switch (answer.toUpperCase()) {
            case "I":
                workplace = coworkingSpace.findIndividualWorkplaceById(workplaceID);
                break;
            case "C":
                workplace = coworkingSpace.findConferenceRoomById(workplaceID);
                break;
            default:
                System.out.println("Неверный выбор. Процесс создания брони прерван.");
                return;
        }

        if (workplace != null) {
            bookingWorkplace(workplaceID, workplace, date, onlineUser, slotNumber, numberOfSlots);
        } else {
            System.out.println("Рабочее место не найдено.");
        }
    }

    /**
     * Резервирует слоты на рабочем месте и добавляет информацию о бронировании в репозиторий.
     *
     * @param workplaceID  идентификатор рабочего места
     * @param workplace    объект, представляющий рабочее место
     * @param date         дата бронирования
     * @param onlineUser   пользователь, создающий бронь
     * @param slotNumber   номер начального слота
     * @param numberOfSlots количество бронируемых слотов
     */
    private void bookingWorkplace(int workplaceID, Workplace workplace, LocalDate date, User onlineUser, int slotNumber, int numberOfSlots) {
        Map<String, Slot> map = null;
        String workplaceType = "";

        if (workplace instanceof IndividualWorkplace) {
            map = coworkingSpace.getIndividualWorkplaces().get(workplace);
            workplaceType = "Индивидуальное рабочее место";
        } else if (workplace instanceof ConferenceRoom) {
            map = coworkingSpace.getConferenceRooms().get(workplace);
            workplaceType = "Конференц-зал";
        }

        if (map == null) {
            System.out.println("Рабочее место не найдено.");
            return;
        }

        String[] reservedSlots = coworkingSpace.reserveSlots(
                workplace, map, workplaceID, date, slotNumber, numberOfSlots
        );

        if (reservedSlots != null) {
            System.out.println("Забронированные слоты:");
            for (String slotDescription : reservedSlots) {
                System.out.println(slotDescription);
            }
            bookingDirectory.addBooking(
                    new Booking(
                            onlineUser.login(), workplaceID, workplaceType, date, reservedSlots
                    )
            );
        } else {
            System.out.println("Не удалось зарезервировать слоты.");
        }
    }

    /**
     * Удаляет бронирование пользователя по логину и дате.
     *
     * @param userLogin логин пользователя
     * @param date      дата бронирования
     */
    public void deleteBooking(String userLogin, LocalDate date) {
        Booking booking = bookingDirectory.getBooking(userLogin, date);
        if (booking == null) {
            System.out.println("Бронирование не найдено.");
            return;
        }

        Workplace workplace = (booking.workplaceType().equals("Индивидуальное рабочее место")) ?
                coworkingSpace.findIndividualWorkplaceById(booking.workplaceID()) :
                coworkingSpace.findConferenceRoomById(booking.workplaceID());

        if (workplace != null) {
            coworkingSpace.setSlotsAvailability(workplace, booking.slots(), true);
        }

        bookingDirectory.removeBooking(booking);
    }

    /**
     * Возвращает строку с информацией о бронированиях пользователя.
     *
     * @param login логин пользователя
     * @return информация о бронированиях пользователя
     */
    public String getBookingsByUser(String login) {
        List<Booking> bookings = bookingDirectory.getBookingsByUser(login);
        if (bookings.isEmpty()) {
            return "У данного пользователя нет бронирований.";
        } else {
            StringBuilder bookingsInfo = new StringBuilder("Бронирования пользователя " + login + ":\n");
            for (Booking booking : bookings) {
                bookingsInfo.append("Дата: ")
                        .append(booking.bookingDate())
                        .append(", Рабочее место: ").append(booking.workplaceType())
                        .append(" #").append(booking.workplaceID())
                        .append(".\nВремя: ")
                        .append(String.join(", ", booking.slots()))
                        .append("\n");
            }
            return bookingsInfo.toString();
        }
    }
}
