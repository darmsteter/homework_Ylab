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
            System.out.println(MessageType.INSTRUCTIONS.getMessage());
            String userResponse = ConsoleUtil.getInput(scan);


            Commands command = Commands.fromString(userResponse);
            if (command == null) {
                System.out.println(MessageType.INVALID_COMMAND_ERROR.getMessage());
                continue;
            }

            switch (command) {
                case REGISTRATION:
                    registration();
                    break;
                case LOG_IN:
                    onlineUserLogin = logIn();
                    break;
                default:
                    System.out.println(MessageType.INVALID_COMMAND_ERROR.getMessage());
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
        System.out.println(MessageType.RETURN_TO_START_PAGE.getMessage());
        System.out.println(MessageType.PROMPT_LOGIN.getMessage());

        String login = ConsoleUtil.getInput(scan);
        if (login.equalsIgnoreCase(Commands.START_PAGE.getCommand())) {
            return "";
        }

        System.out.println(MessageType.PROMPT_PASSWORD.getMessage());
        String password = ConsoleUtil.getInput(scan);

        try {
            User user = userService.validateUser(login, password);
            System.out.println(MessageType.WELCOME_USER.getMessage());
            System.out.println(user.login());
            return user.login();
        } catch (IncorrectPasswordException e) {
            System.out.println(MessageType.INCORRECT_PASSWORD_ERROR.getMessage());
            return "";
        } catch (PersistException | NoSuchUserExistsException e) {
            System.out.println(MessageType.LOGIN_NOT_FOUND_ERROR.getMessage());
            return "";
        }
    }

    /**
     * Регистрация нового пользователя.
     */
    public void registration() {
        System.out.println(MessageType.RETURN_TO_START_PAGE.getMessage());
        System.out.println(MessageType.PROMPT_LOGIN.getMessage());

        String login = ConsoleUtil.getInput(scan);

        if (login.equalsIgnoreCase(Commands.START_PAGE.getCommand())) {
            return;
        }

        try {
            System.out.println(MessageType.PROMPT_PASSWORD.getMessage());
            userService.registerUser(login, ConsoleUtil.getInput(scan));
            System.out.println(MessageType.REGISTRATION_SUCCESS.getMessage());
        } catch (PersistException | WrongDataException | SQLException e) {
            System.out.println(MessageType.RETURN_TO_START_PAGE.getMessage());
            System.out.println(MessageType.LOGIN_ALREADY_EXISTS_ERROR.getMessage());
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

            Commands command = Commands.fromString(userResponse);
            if (command == null) {
                System.out.println(MessageType.INVALID_COMMAND_ERROR.getMessage());
                continue;
            }

            switch (command) {
                case SEARCH_BOOKING_BY_DATE:
                    handleSortedByDate();
                    System.out.println(MessageType.ACTIONS_FOR_USER.getMessage());
                    handleUserActions(onlineUser);
                    break;
                case LIST_OF_WORKPLACES:
                    coworkingSpaceService.getSpaces();
                    System.out.println(MessageType.ACTIONS_FOR_USER.getMessage());
                    handleUserActions(onlineUser);
                    break;
                case CREATE_NEW_BOOKING:
                    createNewBooking(onlineUser);
                    System.out.println(MessageType.ACTIONS_FOR_USER.getMessage());
                    handleUserActions(onlineUser);
                    break;
                case SEARCH_BOOKING_BY_LOGIN:
                    getBookingByUserLogin(onlineUser.login());
                    System.out.println(MessageType.ACTIONS_FOR_USER.getMessage());
                    handleUserActions(onlineUser);
                    break;
                case EXIT:
                    greeting();
                    continueActions = false;
                    break;
                case DELETE_BOOKING:
                    deleteBooking(onlineUser);
                    System.out.println(MessageType.ACTIONS_FOR_USER.getMessage());
                    handleUserActions(onlineUser);
                    break;
                default:
                    System.out.println(MessageType.INVALID_COMMAND_ERROR.getMessage());
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

            Commands command = Commands.fromString(userResponse);
            if (command == null) {
                System.out.println(MessageType.INVALID_COMMAND_ERROR.getMessage());
                continue;
            }

            switch (command) {
                case SEARCH_BOOKING_BY_DATE:
                    handleSortedByDate();
                    System.out.println(MessageType.ACTIONS_FOR_ADMINISTRATOR.getMessage());
                    handleAdminActions(onlineUser);
                    break;
                case CREATE_INDIVIDUAL_WORKPLACE:
                    createNewIndividualWorkplace();
                    System.out.println(MessageType.ACTIONS_FOR_ADMINISTRATOR.getMessage());
                    handleAdminActions(onlineUser);
                    break;
                case CREATE_CONFERENCE_ROOM:
                    createNewConferenceRoom();
                    System.out.println(MessageType.ACTIONS_FOR_ADMINISTRATOR.getMessage());
                    handleAdminActions(onlineUser);
                    break;
                case LIST_OF_WORKPLACES:
                    coworkingSpaceService.getSpaces();
                    System.out.println(MessageType.ACTIONS_FOR_ADMINISTRATOR.getMessage());
                    handleAdminActions(onlineUser);
                    break;
                case CREATE_NEW_BOOKING:
                    createNewBooking(onlineUser);
                    System.out.println(MessageType.ACTIONS_FOR_ADMINISTRATOR.getMessage());
                    handleAdminActions(onlineUser);
                    break;
                case SEARCH_BOOKING_BY_LOGIN:
                    getLoginForSearch();
                    System.out.println(MessageType.ACTIONS_FOR_ADMINISTRATOR.getMessage());
                    handleAdminActions(onlineUser);
                    break;
                case DELETE_BOOKING:
                    deleteBooking(onlineUser);
                    System.out.println(MessageType.ACTIONS_FOR_ADMINISTRATOR.getMessage());
                    handleAdminActions(onlineUser);
                    break;
                case EXIT:
                    greeting();
                    continueActions = false;
                    break;
                default:
                    System.out.println(MessageType.INVALID_COMMAND_ERROR.getMessage());
                    break;
            }
        }
    }

    /**
     * Создает новое индивидуальное рабочее место и добавляет его в базу данных.
     */
    public void createNewIndividualWorkplace() {
        System.out.println(MessageType.PROMPT_CREATING_INDIVIDUAL_WORKPLACE.getMessage());
        String userResponse = ConsoleUtil.getInput(scan);

        Commands command = Commands.fromString(userResponse);
        if (command == null) {
            System.out.println(MessageType.INVALID_COMMAND_ERROR.getMessage());
            return;
        }

        switch (command) {
            case YES:
                try {
                    coworkingSpaceService.addIndividualWorkplaceToDatabase();
                    System.out.println(MessageType.CREATING_WORKPLACE_SUCCESS.getMessage());
                } catch (PersistException e) {
                    System.out.println(MessageType.INTERNAL_ERROR.getMessage());
                }
                break;
            case NO:
                System.out.println(MessageType.CANCEL_CREATING_WORKPLACE.getMessage());
                break;
            default:
                System.out.println(MessageType.INVALID_COMMAND_ERROR.getMessage());
                break;
        }
    }


    /**
     * Создает новый конференц-зал и добавляет его в базу данных.
     */
    public void createNewConferenceRoom() {
        System.out.println(MessageType.PROMPT_CREATING_CONFERENCE_ROOM.getMessage());
        String userResponse = ConsoleUtil.getInput(scan);

        Commands command = Commands.fromString(userResponse);
        if (command == null) {
            System.out.println(MessageType.INVALID_COMMAND_ERROR.getMessage());
            return;
        }

        switch (command) {
            case YES:
                try {
                    System.out.println(MessageType.PROMPT_NUMBER_OF_PLACE.getMessage());
                    int maxCapacity = ConsoleUtil.getInputInt(scan);
                    ConsoleUtil.getInput(scan);
                    coworkingSpaceService.addConferenceRoomToDatabase(maxCapacity);
                    System.out.println(MessageType.CREATING_WORKPLACE_SUCCESS.getMessage());
                } catch (PersistException e) {
                    System.out.println(MessageType.INTERNAL_ERROR.getMessage());
                }
                break;
            case NO:
                System.out.println(MessageType.CANCEL_CREATING_WORKPLACE.getMessage());
                break;
            default:
                System.out.println(MessageType.INVALID_COMMAND_ERROR.getMessage());
                break;
        }
    }

    /**
     * Создает новую бронь для пользователя.
     *
     * @param onlineUser объект пользователя
     */
    public void createNewBooking(User onlineUser) {
        LocalDate date = getDateInput();
        if (date == null) {
            return;
        }

        System.out.println(MessageType.PROMPT_WORKPLACE_NUMBER.getMessage());
        int workplaceID = ConsoleUtil.getInputInt(scan);
        ConsoleUtil.getInput(scan);

        System.out.println(MessageType.INSTRUCTION_FOR_SLOTS_PROMPT.getMessage());
        int slotNumber = ConsoleUtil.getInputInt(scan);
        int numberOfSlots = ConsoleUtil.getInputInt(scan);
        ConsoleUtil.getInput(scan);

        try {
            bookingService.createNewBooking(onlineUser, date, workplaceID, slotNumber, numberOfSlots);
            System.out.println(MessageType.BOOKING_SUCCESS.getMessage());
        } catch (PersistException e) {
            System.out.println(MessageType.BOOKING_CREATION_ERROR.getMessage() + e.getMessage());
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
            System.out.println(MessageType.PROMPT_LOGIN_BOOKING_DELETE.getMessage());
            userLogin = ConsoleUtil.getInput(scan);
        } else {
            userLogin = onlineUser.getLogin();
        }

        LocalDate date = getDateInput();
        if (date == null) {
            return;
        }

        try {
            List<Booking> bookings = bookingRepository.getBookingsByDateAndUser(Date.valueOf(date), userRepository.getUsersByLogin(userLogin).get(0).getPK());
            if (bookings == null) {
                System.out.println(MessageType.BOOKING_SEARCH_ERROR.getMessage());
                return;
            }

            System.out.println(MessageType.LIST_OF_BOOKING.getMessage());
            for (int i = 0; i < bookings.size(); i++) {
                System.out.println((i + 1) + ". " + bookings.get(i));
            }

            System.out.println(MessageType.PROMPT_DELETING_BOOKING.getMessage());
            int choice = Integer.parseInt(ConsoleUtil.getInput(scan));

            bookingService.deleteBooking(onlineUser, userLogin, date.toString(), choice);
        } catch (PersistException | WrongDataException e) {
            System.out.println(MessageType.BOOKING_DELETE_ERROR.getMessage() + e.getMessage());
        }
    }

    /**
     * Обрабатывает сортировку по дате.
     */
    private void handleSortedByDate() {
        System.out.println(MessageType.PROMPT_DATE.getMessage());
        String dateInput = ConsoleUtil.getInput(scan);
        try {
            LocalDate date = LocalDate.parse(dateInput, DateTimeFormatter.ISO_LOCAL_DATE);
            List<Booking> bookings = bookingRepository.getBookingsByDateAndUser(Date.valueOf(date), null);

            if (bookings == null) {
                System.out.println(MessageType.BOOKING_SEARCH_ERROR.getMessage());
                return;
            }

            for (int i = 0; i < bookings.size(); i++) {
                System.out.println((i + 1) + ". " + bookings.get(i));
            }
            System.out.println();
        } catch (DateTimeParseException e) {
            System.out.println(MessageType.INCORRECT_DATE_ERROR.getMessage());
        } catch (PersistException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Запрашивает логин пользователя для вывода списка всех броней, принадлежащих этому пользователю.
     */
    public void getLoginForSearch() throws PersistException {
        System.out.println(MessageType.PROMPT_LOGIN_BOOKING_SEARCH.getMessage());
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
                System.out.println(MessageType.LOGIN_NOT_FOUND_ERROR.getMessage());
                return;
            }

            int userId = user.getPK();

            List<Booking> bookings = bookingRepository.getBookingsByDateAndUser(null, userId);

            if (bookings == null) {
                System.out.println(MessageType.BOOKING_DOESNT_EXIST_ERROR.getMessage());
            } else {
                System.out.println(MessageType.LIST_OF_BOOKING.getMessage());
                for (Booking booking : bookings) {
                    System.out.println("Идентификатор брони: " + booking.getPK() + ", Дата брони: " + booking.getBookingDate());
                }
            }
        } catch (PersistException e) {
            System.out.println(MessageType.INTERNAL_ERROR.getMessage());
            throw e;
        }
    }

    /**
     * Запрашивает дату у пользователя, обрабатывает ввод и возвращает LocalDate.
     *
     * @return введённая пользователем дата или null в случае некорректного ввода
     */
    private LocalDate getDateInput() {
        System.out.println(MessageType.PROMPT_BOOKING_DATE.getMessage());
        System.out.println(MessageType.PROMPT_DATE.getMessage());
        String dateInput = ConsoleUtil.getInput(scan);

        LocalDate date;
        try {
            date = LocalDate.parse(dateInput, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            System.out.println(MessageType.INCORRECT_DATE_ERROR.getMessage());
            return null;
        }
        return date;
    }
}