package com.coworking_service.service;

import com.coworking_service.entity.Booking;
import com.coworking_service.entity.User;
import com.coworking_service.entity.enums.Role;
import com.coworking_service.exception.PersistException;
import com.coworking_service.exception.WrongDataException;
import com.coworking_service.repository.BookingRepository;
import com.coworking_service.repository.UserRepository;
import com.coworking_service.service.interfaces.BookingService;
import com.coworking_service.service.interfaces.SlotService;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final SlotService slotService;
    private final UserRepository userRepository;

    public BookingServiceImpl(
            BookingRepository bookingRepository,
            SlotService slotService,
            UserRepository userRepository
    ) {
        this.bookingRepository = bookingRepository;
        this.slotService = slotService;
        this.userRepository = userRepository;
    }

    /**
     * Создает новую бронь для пользователя.
     *
     * @param onlineUser    объект пользователя
     * @param date          дата бронирования
     * @param workplaceID   идентификатор рабочего пространства
     * @param slotNumber    номер первого бронируемого слота
     * @param numberOfSlots количество бронируемых слотов
     * @throws PersistException   если произошла ошибка при доступе к базе данных
     * @throws SQLException       если произошла ошибка при выполнении SQL-запроса
     * @throws WrongDataException если введены некорректные данные
     */
    public void createNewBooking(User onlineUser, LocalDate date, int workplaceID, int slotNumber, int numberOfSlots)
            throws PersistException, SQLException, WrongDataException {
        if (date.isBefore(LocalDate.now())) {
            throw new WrongDataException("Вы не можете забронировать рабочее пространство на прошедшую дату.");
        }

        Time bookingTimeFrom = slotService.calculateBookingTimeFrom(slotNumber);
        Time bookingTimeTo = slotService.calculateBookingTimeTo(slotNumber, numberOfSlots);

        boolean overlap = bookingRepository.hasOverlap(workplaceID, Date.valueOf(date), bookingTimeFrom, bookingTimeTo);
        if (overlap) {
            throw new PersistException("На указанное время это рабочее место уже занято. Пожалуйста, выберите другое время или место.");
        }

        int userId = onlineUser.getPK();

        Booking booking = new Booking(null, userId, workplaceID, Date.valueOf(date), bookingTimeFrom, bookingTimeTo);
        bookingRepository.create(booking);
    }

    /**
     * Удаляет бронирование пользователя по логину, дате и выбранному номеру бронирования.
     *
     * @param onlineUser объект пользователя, выполняющего операцию
     * @param userLogin  логин пользователя, чью бронь нужно удалить
     * @param dateInput  дата бронирования в формате "yyyy-MM-dd"
     * @param choice     номер выбранного бронирования для удаления
     * @throws PersistException   если произошла ошибка при доступе к базе данных
     * @throws WrongDataException если введены некорректные данные
     */
    public void deleteBooking(User onlineUser, String userLogin, String dateInput, int choice)
            throws PersistException, WrongDataException {
        LocalDate date;
        try {
            date = LocalDate.parse(dateInput, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            throw new WrongDataException("Некорректный формат даты. Процесс удаления брони прерван.");
        }

        User user;
        if (onlineUser.getRole().equals(Role.ADMINISTRATOR.name())) {
            user = userRepository.getUsersByLogin(userLogin).get(0);
        } else {
            user = userRepository.getUsersByLogin(onlineUser.getLogin()).get(0);
        }

        List<Booking> bookings = bookingRepository.getBookingsByDateAndUser(Date.valueOf(date), user.getPK());

        if (bookings.isEmpty()) {
            System.out.println("У пользователя нет бронирований на указанную дату.");
            return;
        }

        if (choice < 1 || choice > bookings.size()) {
            throw new WrongDataException("Некорректный номер бронирования.");
        }

        Booking bookingToDelete = bookings.get(choice - 1);
        bookingRepository.delete(bookingToDelete.getPK());

        System.out.println("Бронь успешно удалена.");
    }
}
