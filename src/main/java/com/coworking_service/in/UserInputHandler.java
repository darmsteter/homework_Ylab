package com.coworking_service.in;

import com.coworking_service.entity.Booking;
import com.coworking_service.entity.User;
import com.coworking_service.exception.*;
import com.coworking_service.entity.enums.Commands;
import com.coworking_service.entity.enums.MessageType;
import com.coworking_service.entity.enums.Role;
import com.coworking_service.repository.BookingRepository;
import com.coworking_service.repository.UserRepository;
import com.coworking_service.service.BookingServiceImpl;
import com.coworking_service.service.CoworkingSpaceServiceImpl;
import com.coworking_service.service.SlotServiceImpl;
import com.coworking_service.service.UserServiceImpl;
import com.coworking_service.service.interfaces.BookingService;
import com.coworking_service.service.interfaces.CoworkingSpaceService;
import com.coworking_service.service.interfaces.SlotService;
import com.coworking_service.service.interfaces.UserService;
import com.coworking_service.util.ConsoleUtil;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

/**
 * Класс для обработки вводимых пользователем данных.
 */
public class UserInputHandler {
    private final Scanner scan = new Scanner(System.in);

    private final UserRepository userRepository = new UserRepository();
    private final BookingRepository bookingRepository = new BookingRepository();

    private final CoworkingSpaceService coworkingSpaceService = new CoworkingSpaceServiceImpl();
    private final UserService userService = new UserServiceImpl(userRepository);
    private final SlotService slotService = new SlotServiceImpl();
    private final BookingService bookingService = new BookingServiceImpl(bookingRepository, slotService, userRepository);

    /**
     * Конструктор класса UserInputHandler.
     */
    public UserInputHandler() {}

    /**
     * Приветствие пользователя и вход в систему или регистрация.
     *
     * @return логин онлайн пользователя
     */
    public String greeting() {
        String onlineUserLogin = "";

        while (onlineUserLogin.isEmpty()) {
            ConsoleUtil.printMessage(MessageType.INSTRUCTIONS);
            String userResponse = ConsoleUtil.getInput(scan);

            switch (userResponse.toUpperCase()) {
                case "R":
                    registration();
                    break;
                case "L":
                    onlineUserLogin = logIn();
                    break;
                default:
                    ConsoleUtil.printMessage(MessageType.INVALID_COMMAND_ERROR);
                    break;
            }
        }
        return onlineUserLogin;
    }

    /**
     * Вход пользователя в систему.
     *
     * @return логин пользователя
     * @throws NoSuchUserExistsException если пользователь с указанным логином не найден
     */
    private String logIn() throws NoSuchUserExistsException {
        ConsoleUtil.printMessage(MessageType.RETURN_TO_START_PAGE);
        ConsoleUtil.printMessage(MessageType.PROMPT_LOGIN);

        String login = ConsoleUtil.getInput(scan);
        if (login.equalsIgnoreCase(Commands.START_PAGE.getCommand())) {
            return "";
        }

        ConsoleUtil.printMessage(MessageType.PROMPT_PASSWORD);
        String password = ConsoleUtil.getInput(scan);

        try {
            User user = userService.validateUser(login, password);
            ConsoleUtil.printMessage(MessageType.WELCOME_USER);
            System.out.println(user.login());
            return user.login();
        } catch (IncorrectPasswordException e) {
            ConsoleUtil.printMessage(MessageType.INCORRECT_PASSWORD_ERROR);
            return "";
        } catch (PersistException | NoSuchUserExistsException e) {
            ConsoleUtil.printMessage(MessageType.LOGIN_NOT_FOUND_ERROR);
            return "";
        }
    }

    /**
     * Регистрация нового пользователя.
     */
    public void registration() {
        ConsoleUtil.printMessage(MessageType.RETURN_TO_START_PAGE);
        ConsoleUtil.printMessage(MessageType.PROMPT_LOGIN);

        String login = ConsoleUtil.getInput(scan);

        if (login.equalsIgnoreCase(Commands.START_PAGE.getCommand())) {
            return;
        }

        try {
            ConsoleUtil.printMessage(MessageType.PROMPT_PASSWORD);
            userService.registerUser(login, ConsoleUtil.getInput(scan));
            ConsoleUtil.printMessage(MessageType.REGISTRATION_SUCCESS);
        } catch (PersistException | WrongDataException | SQLException e) {
            ConsoleUtil.printMessage(MessageType.RETURN_TO_START_PAGE);
            ConsoleUtil.printMessage(MessageType.LOGIN_ALREADY_EXISTS_ERROR);
        }
    }

    /**
     * Обрабатывает действия обычного пользователя.
     *
     * @param onlineUser объект пользователя
     */
    public void handleUserActions(User onlineUser) throws PersistException {
        boolean continueActions = true;
        while (continueActions) {
            String userResponse = ConsoleUtil.getInput(scan);

            switch (userResponse.toUpperCase()) {
                case "D":
                    handleSortedByDate();
                    ConsoleUtil.printMessage(MessageType.ACTIONS_FOR_USER);
                    handleUserActions(onlineUser);
                    break;
                case "L":
                    coworkingSpaceService.getSpaces();
                    ConsoleUtil.printMessage(MessageType.ACTIONS_FOR_USER);
                    handleUserActions(onlineUser);
                    break;
                case "B":
                    createNewBooking(onlineUser);
                    ConsoleUtil.printMessage(MessageType.ACTIONS_FOR_USER);
                    handleUserActions(onlineUser);
                    break;
                case "M":
                    getBookingByUserLogin(onlineUser.login());
                    ConsoleUtil.printMessage(MessageType.ACTIONS_FOR_USER);
                    handleUserActions(onlineUser);
                    break;
                case "E":
                    greeting();
                    continueActions = false;
                    break;
                case "R":
                    deleteBooking(onlineUser);
                    ConsoleUtil.printMessage(MessageType.ACTIONS_FOR_USER);
                    handleUserActions(onlineUser);
                    break;
                default:
                    ConsoleUtil.printMessage(MessageType.INVALID_COMMAND_ERROR);
                    break;
            }
        }
    }

    /**
     * Обрабатывает действия администратора.
     *
     * @param onlineUser объект пользователя
     */
    public void handleAdminActions(User onlineUser) throws PersistException {
        boolean continueActions = true;
        while (continueActions) {
            String userResponse = ConsoleUtil.getInput(scan);

            switch (userResponse.toUpperCase()) {
                case "D":
                    handleSortedByDate();
                    ConsoleUtil.printMessage(MessageType.ACTIONS_FOR_ADMINISTRATOR);
                    handleAdminActions(onlineUser);
                    break;
                case "I":
                    createNewIndividualWorkplace();
                    ConsoleUtil.printMessage(MessageType.ACTIONS_FOR_ADMINISTRATOR);
                    handleAdminActions(onlineUser);
                    break;
                case "C":
                    createNewConferenceRoom();
                    ConsoleUtil.printMessage(MessageType.ACTIONS_FOR_ADMINISTRATOR);
                    handleAdminActions(onlineUser);
                    break;
                case "L":
                    coworkingSpaceService.getSpaces();
                    ConsoleUtil.printMessage(MessageType.ACTIONS_FOR_ADMINISTRATOR);
                    handleAdminActions(onlineUser);
                    break;
                case "B":
                    createNewBooking(onlineUser);
                    ConsoleUtil.printMessage(MessageType.ACTIONS_FOR_ADMINISTRATOR);
                    handleAdminActions(onlineUser);
                    break;
                case "M":
                    getLoginForSearch();
                    ConsoleUtil.printMessage(MessageType.ACTIONS_FOR_ADMINISTRATOR);
                    handleAdminActions(onlineUser);
                    break;
                case "R":
                    deleteBooking(onlineUser);
                    ConsoleUtil.printMessage(MessageType.ACTIONS_FOR_ADMINISTRATOR);
                    handleAdminActions(onlineUser);
                    break;
                case "E":
                    greeting();
                    continueActions = false;
                    break;
                default:
                    ConsoleUtil.printMessage(MessageType.INVALID_COMMAND_ERROR);
                    break;
            }
        }
    }

    /**
     * Создает новое индивидуальное рабочее место и добавляет его в базу данных.
     */
    public void createNewIndividualWorkplace() {
        ConsoleUtil.printMessage(MessageType.CREATING_INDIVIDUAL_WORKPLACE);
        String userResponse = ConsoleUtil.getInput(scan);

        switch (userResponse.toUpperCase()) {
            case "Y":
                try {
                    coworkingSpaceService.addIndividualWorkplaceToDatabase();
                    ConsoleUtil.printMessage(MessageType.CREATING_WORKPLACE_SUCCESS);
                } catch (PersistException e) {
                    ConsoleUtil.printMessage(MessageType.INTERNAL_ERROR);
                }
                break;
            case "N":
                ConsoleUtil.printMessage(MessageType.CANCEL_CREATING_WORKPLACE);
                break;
            default:
                ConsoleUtil.printMessage(MessageType.INVALID_COMMAND_ERROR);
                break;
        }
    }


    /**
     * Создает новый конференц-зал и добавляет его в базу данных.
     */
    public void createNewConferenceRoom() {
        ConsoleUtil.printMessage(MessageType.CREATING_CONFERENCE_ROOM);
        String userResponse = ConsoleUtil.getInput(scan);

        switch (userResponse.toUpperCase()) {
            case "Y":
                try {
                    ConsoleUtil.printMessage(MessageType.NUMBER_OF_PLACE);
                    int maxCapacity = scan.nextInt();
                    scan.nextLine();
                    coworkingSpaceService.addConferenceRoomToDatabase(maxCapacity);
                    ConsoleUtil.printMessage(MessageType.CREATING_WORKPLACE_SUCCESS);
                } catch (PersistException e) {
                    ConsoleUtil.printMessage(MessageType.INTERNAL_ERROR);
                }
                break;
            case "N":
                ConsoleUtil.printMessage(MessageType.CANCEL_CREATING_WORKPLACE);
                break;
            default:
                ConsoleUtil.printMessage(MessageType.INVALID_COMMAND_ERROR);
                break;
        }
    }

    /**
     * Создает новую бронь для пользователя.
     *
     * @param onlineUser объект пользователя
     */
    public void createNewBooking(User onlineUser) {
        System.out.println("На какую дату вы хотите создать бронь? Формат ГГГГ-ММ-ДД");
        String dateInput = ConsoleUtil.getInput(scan);

        LocalDate date;
        try {
            date = LocalDate.parse(dateInput, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            System.out.println("Некорректный формат даты. Процесс создания брони прерван.");
            return;
        }

        System.out.println("Введите номер нужного вам рабочего пространства:");
        int workplaceID = scan.nextInt();
        scan.nextLine();

        System.out.println("""
                Введите номер слота, который вы хотите забронировать и количество слотов.
                Например, для брони с 9:00 до 13:00 необходимо ввести
                2
                4\
                       \s
                2 - номер первого бронируемого слота, 4 - количество бронируемых слотов.""");
        int slotNumber = scan.nextInt();
        int numberOfSlots = scan.nextInt();
        scan.nextLine();

        try {
            bookingService.createNewBooking(onlineUser, date, workplaceID, slotNumber, numberOfSlots);
            System.out.println("Бронь успешно создана.");
        } catch (PersistException e) {
            System.out.println("Ошибка при создании брони: " + e.getMessage());
        } catch (SQLException | WrongDataException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Удаляет бронирование пользователя по логину, дате и начальному слоту.
     */
    public void deleteBooking(User onlineUser) {
        String userLogin;
        if (Objects.equals(onlineUser.getRole(), Role.ADMINISTRATOR.name())) {
            System.out.println("Введите логин пользователя, чью бронь вы хотите удалить:");
            userLogin = scan.nextLine();
        } else {
            userLogin = onlineUser.getLogin();
        }

        System.out.println("Введите дату бронирования (формат ГГГГ-ММ-ДД):");
        String dateInput = scan.nextLine();

        LocalDate date;
        try {
            date = LocalDate.parse(dateInput, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            System.out.println("Некорректный формат даты. Процесс создания брони прерван.");
            return;
        }

        try {
            List<Booking> bookings = bookingRepository.getBookingsByDateAndUser(Date.valueOf(date), userRepository.getUsersByLogin(userLogin).get(0).getPK());
            if (bookings == null) {
                System.out.println("У пользователя нет бронирований на указанную дату.");
                return;
            }

            System.out.println("Список бронирований пользователя на указанную дату:");
            for (int i = 0; i < bookings.size(); i++) {
                System.out.println((i + 1) + ". " + bookings.get(i));
            }

            System.out.println("Введите номер бронирования для удаления:");
            int choice = Integer.parseInt(ConsoleUtil.getInput(scan));

            bookingService.deleteBooking(onlineUser, userLogin, dateInput, choice);
        } catch (PersistException | WrongDataException e) {
            System.out.println("Ошибка при удалении бронирования: " + e.getMessage());
        }
    }

    /**
     * Обрабатывает сортировку по дате.
     */
    private void handleSortedByDate() {
        ConsoleUtil.printMessage(MessageType.DATE);
        String dateInput = ConsoleUtil.getInput(scan);
        try {
            LocalDate date = LocalDate.parse(dateInput, DateTimeFormatter.ISO_LOCAL_DATE);
            List<Booking> bookings = bookingRepository.getBookingsByDateAndUser(Date.valueOf(date), null);

            if (bookings == null) {
                System.out.println("Нет бронирований на указанную дату.");
                return;
            }

            for (int i = 0; i < bookings.size(); i++) {
                System.out.println((i + 1) + ". " + bookings.get(i));
            }
            System.out.println();
        } catch (DateTimeParseException e) {
            System.out.println("Некорректный формат даты.");
        } catch (PersistException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Запрашивает логин пользователя для вывода списка всех броней, принадлежащих этому пользователю.
     */
    public void getLoginForSearch() throws PersistException {
        System.out.println("Введите логин пользователя, чьи брони вы хотите посмотреть:");
        getBookingByUserLogin(ConsoleUtil.getInput(scan));
    }

    /**
     * Выводит список всех броней пользователя по его логину из базы данных.
     *
     * @param login логин пользователя
     * @throws PersistException если возникает ошибка при работе с базой данных
     */
    public void getBookingByUserLogin(String login) throws PersistException {
        try {
            User user = userRepository.getUsersByLogin(login).get(0);

            if (user == null) {
                System.out.println("Пользователь с логином '" + login + "' не найден.");
                return;
            }

            int userId = user.getPK();

            List<Booking> bookings = bookingRepository.getBookingsByDateAndUser(null, userId);

            if (bookings == null) {
                System.out.println("У пользователя с логином '" + login + "' нет бронирований.");
            } else {
                System.out.println("Брони пользователя '" + login + "':");
                for (Booking booking : bookings) {
                    System.out.println("Идентификатор брони: " + booking.getPK() + ", Дата брони: " + booking.getBookingDate());
                }
            }
        } catch (PersistException e) {
            System.out.println("Ошибка при получении данных из базы данных.");
            throw e;
        }
    }
}