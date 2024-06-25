package com.coworking_service.in;

import com.coworking_service.exception.IncorrectPasswordException;
import com.coworking_service.exception.NoSuchUserExistsException;
import com.coworking_service.exception.UserAlreadyExistsException;
import com.coworking_service.model.*;
import com.coworking_service.model.enums.Commands;
import com.coworking_service.model.enums.MessageType;
import com.coworking_service.model.enums.Role;
import com.coworking_service.service.interfaces.BookingService;
import com.coworking_service.service.interfaces.CoworkingSpaceService;
import com.coworking_service.service.interfaces.UserDirectoryService;
import com.coworking_service.util.ConsoleUtil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Класс для обработки вводимых пользователем данных.
 */
public class UserInputHandler {
    private final Scanner scan = new Scanner(System.in);
    private final BookingService bookingService;
    private final UserDirectoryService userDirectoryService;
    private final CoworkingSpaceService coworkingSpaceService;

    /**
     * Конструктор класса UserInputHandler.
     *
     * @param bookingService        сервис управления бронированиями
     * @param userDirectoryService  сервис управления пользовательскими данными
     * @param coworkingSpaceService сервис управления рабочими пространствами
     */
    public UserInputHandler(
            BookingService bookingService,
            UserDirectoryService userDirectoryService,
            CoworkingSpaceService coworkingSpaceService
    ) {
        this.bookingService = bookingService;
        this.userDirectoryService = userDirectoryService;
        this.coworkingSpaceService = coworkingSpaceService;
    }

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
        try {
            String userLogin = userDirectoryService.logIn(login, scan);
            ConsoleUtil.printMessage(MessageType.WELCOME_USER);
            System.out.println(userLogin);
            return userLogin;
        } catch (NoSuchUserExistsException e) {
            ConsoleUtil.printMessage(MessageType.LOGIN_NOT_FOUND_ERROR);
        } catch (IncorrectPasswordException e) {
            ConsoleUtil.printMessage(MessageType.INCORRECT_PASSWORD_ERROR);
        }
        return "";
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
            userDirectoryService.registerUser(login, scan);
            ConsoleUtil.printMessage(MessageType.REGISTRATION_SUCCESS);
        } catch (UserAlreadyExistsException e) {
            ConsoleUtil.printMessage(MessageType.RETURN_TO_START_PAGE);
            ConsoleUtil.printMessage(MessageType.LOGIN_ALREADY_EXISTS_ERROR);
        }
    }

    /**
     * Обрабатывает действия обычного пользователя.
     *
     * @param onlineUser объект пользователя
     */
    public void handleUserActions(User onlineUser) {
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
    public void handleAdminActions(User onlineUser) {
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
     * Создает новое индивидуальное рабочее место.
     */
    public void createNewIndividualWorkplace() {
        ConsoleUtil.printMessage(MessageType.CREATING_INDIVIDUAL_WORKPLACE);
        String userResponse = ConsoleUtil.getInput(scan);
        switch (userResponse.toUpperCase()) {
            case "Y":
                coworkingSpaceService.addIndividualWorkplace();
                ConsoleUtil.printMessage(MessageType.CREATING_WORKPLACE_SUCCESS);
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
     * Создает новый конференц-зал.
     */
    public void createNewConferenceRoom() {
        ConsoleUtil.printMessage(MessageType.CREATING_CONFERENCE_ROOM);
        String userResponse = ConsoleUtil.getInput(scan);

        switch (userResponse.toUpperCase()) {
            case "Y":
                ConsoleUtil.printMessage(MessageType.NUMBER_OF_PLACE);
                int maxCapacity = scan.nextInt();
                scan.nextLine();
                coworkingSpaceService.addConferenceRoom(maxCapacity);
                ConsoleUtil.printMessage(MessageType.CREATING_WORKPLACE_SUCCESS);
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

            if (date.isBefore(LocalDate.now())) {
                System.out.println("Вы не можете забронировать рабочее пространство на прошедшую дату.");
                return;
            }
        } catch (DateTimeParseException e) {
            System.out.println("Некорректный формат даты. Процесс создания брони прерван.");
            return;
        }

        System.out.println(
                "Вы хотите забронировать Индивидуальное рабочее место (I) или конференц зал(C)?"
        );
        String answer = ConsoleUtil.getInput(scan);
        System.out.println("Введите номер нужного вам рабочего пространства:");
        int workplaceID = scan.nextInt();
        scan.nextLine();

        System.out.println("""
                Введите номер слота, который вы хотите забронировать и количество слотов.
                Например, для брони с 9:00 до 13:00 необходимо ввести\s
                2\s
                4\
                
                2 - номер первого бронируемого слота, 4 - количество бронируемых слотов.""");
        int slotNumber = scan.nextInt();
        int numberOfSlots = scan.nextInt();
        scan.nextLine();

        bookingService.createNewBooking(onlineUser, date, answer, workplaceID, slotNumber, numberOfSlots);
    }

    /**
     * Удаляет бронирование пользователя по логину, дате и начальному слоту.
     */
    public void deleteBooking(User onlineUser) {
        String userLogin;
        if (onlineUser.role().equals(Role.ADMINISTRATOR)) {
            System.out.println("Введите логин пользователя, чью бронь вы хотите удалить:");
            userLogin = ConsoleUtil.getInput(scan);

            if (!userDirectoryService.checkIsUserExist(userLogin)) {
                System.out.println("Пользователь с таким логином не существует.");
                return;
            }
        } else {
            userLogin = onlineUser.login();
        }

        System.out.println("Введите дату бронирования (формат ГГГГ-ММ-ДД):");
        String dateInput = ConsoleUtil.getInput(scan);
        LocalDate date;
        try {
            date = LocalDate.parse(dateInput, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            System.out.println("Некорректный формат даты. Процесс удаления брони прерван.");
            return;
        }

        bookingService.deleteBooking(userLogin, date);

        System.out.println("Бронирование удалено.");
    }

    /**
     * Обрабатывает сортировку по дате.
     */
    private void handleSortedByDate() {
        ConsoleUtil.printMessage(MessageType.DATE);
        String dateInput = ConsoleUtil.getInput(scan);
        try {
            LocalDate date = LocalDate.parse(dateInput, DateTimeFormatter.ISO_LOCAL_DATE);
            coworkingSpaceService.sortedByDate(date);
        } catch (DateTimeParseException e) {
            System.out.println("Некорректный формат даты.");
        }
    }

    /**
     * Запрашивает логин пользователя для вывода списка всех броней, принадлежащих этому пользователю.
     */
    public void getLoginForSearch() {
        System.out.println("Введите логин пользователя, чьи брони вы хотите посмотреть:");
        getBookingByUserLogin(ConsoleUtil.getInput(scan));
    }

    /**
     * Выводит список всех броней пользователя по его логину.
     *
     * @param login логин пользователя
     */
    public void getBookingByUserLogin(String login) {
        String bookings = bookingService.getBookingsByUser(login);
        System.out.println(bookings);
    }
}