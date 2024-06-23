package com.coworking_service.in;

import com.coworking_service.exception.NoSuchUserExistsException;
import com.coworking_service.model.*;
import com.coworking_service.model.enums.Commands;
import com.coworking_service.model.enums.MessageType;
import com.coworking_service.model.enums.Role;
import com.coworking_service.service.interfaces.UserDirectoryService;
import com.coworking_service.util.ConsoleUtil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.Scanner;

/**
 * Класс для обработки вводимых пользователем данных.
 */
public class UserInputHandler {
    private final Scanner scan = new Scanner(System.in);
    private final UserDirectoryService userDirectoryService;
    private final CoworkingSpace coworkingSpace;
    private final BookingDirectory bookingDirectory;

    /**
     * Конструктор класса UserInputHandler.
     *
     * @param userDirectoryService сервис для работы с пользователями
     */
    public UserInputHandler(
            UserDirectoryService userDirectoryService,
            CoworkingSpace coworkingSpace,
            BookingDirectory bookingDirectory) {
        this.userDirectoryService = userDirectoryService;
        this.coworkingSpace = coworkingSpace;
        this.bookingDirectory = bookingDirectory;
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
        if (userDirectoryService.checkIsUserExist(login)) {
            User currentUser = userDirectoryService.findUserByLogin(login);

            ConsoleUtil.printMessage(MessageType.RETURN_TO_START_PAGE);
            ConsoleUtil.printMessage(MessageType.PROMPT_PASSWORD);

            while (true) {
                String password = ConsoleUtil.getInput(scan);

                if (password.equals(currentUser.password())) {
                    ConsoleUtil.printMessage(MessageType.WELCOME_USER);
                    System.out.println(currentUser.login());
                    return currentUser.login();
                } else if (password.equalsIgnoreCase(Commands.START_PAGE.getCommand())) {
                    return "";
                } else {
                    ConsoleUtil.printMessage(MessageType.RETURN_TO_START_PAGE);
                    ConsoleUtil.printMessage(MessageType.INCORRECT_PASSWORD_ERROR);
                }
            }
        } else {
            ConsoleUtil.printMessage(MessageType.RETURN_TO_START_PAGE);
            ConsoleUtil.printMessage(MessageType.LOGIN_NOT_FOUND_ERROR);
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

        if (userDirectoryService.checkIsUserExist(login)) {
            ConsoleUtil.printMessage(MessageType.RETURN_TO_START_PAGE);
            ConsoleUtil.printMessage(MessageType.LOGIN_ALREADY_EXISTS_ERROR);
        } else {
            ConsoleUtil.printMessage(MessageType.RETURN_TO_START_PAGE);
            ConsoleUtil.printMessage(MessageType.PROMPT_PASSWORD);

            String password = ConsoleUtil.getInput(scan);

            if (password.equalsIgnoreCase(Commands.START_PAGE.getCommand())) {
                return;
            }

            userDirectoryService.userDirectory().addNewUser(
                    login, new User(login, password, Role.USER)
            );

            ConsoleUtil.printMessage(MessageType.REGISTRATION_SUCCESS);
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
                coworkingSpace.addIndividualWorkplace();
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
                int max = scan.nextInt();
                coworkingSpace.addConferenceRoom(max);
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
        int workplaceID;
        Workplace workplace;
        switch (answer.toUpperCase()) {
            case "I":
                coworkingSpace.getIndividualWorkplacesSlotsByDate(date);
                workplaceID = scan.nextInt();
                workplace = coworkingSpace.findIndividualWorkplaceById(workplaceID);
                bookingWorkplace(workplaceID, workplace, date, onlineUser);
                break;
            case "C":
                coworkingSpace.getConferenceRoomsSlotsByDate(date);
                workplaceID = scan.nextInt();
                workplace = coworkingSpace.findConferenceRoomById(workplaceID);
                bookingWorkplace(workplaceID, workplace, date, onlineUser);
                break;
            default:
        }
    }

    /**
     * Резервирует слоты для рабочего места или конференц-зала.
     *
     * @param workplaceID идентификатор рабочего места или конференц-зала
     * @param workplace   объект рабочего места или конференц-зала
     * @param date        дата бронирования
     * @param onlineUser  объект пользователя
     */
    private void bookingWorkplace(int workplaceID, Workplace workplace, LocalDate date, User onlineUser) {
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
            createNewBooking(onlineUser);
        }

        System.out.println("Введите номер слота, который вы хотите забронировать и количество слотов.\n" +
                "Например, для брони с 9:00 до 13:00 необходимо ввести \n2 \n4" +
                "\n2 - номер первого бронируемого слота, 4 - количество бронируемых слотов.");
        int slotNumber = scan.nextInt();
        int numberOfSlots = scan.nextInt();
        scan.nextLine();

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
     * Удаляет бронирование пользователя по логину, дате и начальному слоту.
     */
    public void deleteBooking() {
        System.out.println("Введите логин пользователя, чью бронь вы хотите удалить:");
        String userLogin = ConsoleUtil.getInput(scan);

        if (!userDirectoryService.checkIsUserExist(userLogin)) {
            System.out.println("Пользователь с таким логином не существует.");
            return;
        }

        System.out.println("Введите дату бронирования (формат ГГГГ-ММ-ДД):");
        String dateInput = ConsoleUtil.getInput(scan);
        LocalDate date = LocalDate.parse(dateInput, DateTimeFormatter.ISO_LOCAL_DATE);

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

        System.out.println("Бронирование удалено.");
    }
}