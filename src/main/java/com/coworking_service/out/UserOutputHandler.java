package com.coworking_service.out;

import com.coworking_service.in.UserInputHandler;
import com.coworking_service.model.*;
import com.coworking_service.model.enums.MessageType;
import com.coworking_service.model.enums.Role;
import com.coworking_service.service.interfaces.UserDirectoryService;
import com.coworking_service.util.ConsoleUtil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * Класс для обработки вывода данных пользователю в консольном приложении.
 */
public class UserOutputHandler {
    private final Scanner scan = new Scanner(System.in);
    private final UserInputHandler userInputHandler;
    private final CoworkingSpace coworkingSpace;

    /**
     * Конструктор для создания объекта UserOutputHandler.
     *
     * @param userDirectoryService сервис для работы с пользователями
     * @param coworkingSpace       объект коворкинг-пространства
     */
    public UserOutputHandler(UserDirectoryService userDirectoryService, CoworkingSpace coworkingSpace) {
        this.userInputHandler = new UserInputHandler(userDirectoryService, coworkingSpace);
        this.coworkingSpace = coworkingSpace;
    }

    /**
     * Приветствует пользователя в зависимости от его роли (администратор или обычный пользователь).
     *
     * @param onlineUser объект пользователя
     */
    public void greetingsForOnlineUser(User onlineUser) {
        if (onlineUser.role().equals(Role.ADMINISTRATOR)) {
            greetingsForAdmin(onlineUser);
        } else {
            greetingsForUser(onlineUser);
        }
    }

    /**
     * Приветствует обычного пользователя и предоставляет ему варианты действий.
     *
     * @param onlineUser объект пользователя
     */
    private void greetingsForUser(User onlineUser) {
        boolean continueActions = true;
        while (continueActions) {
            ConsoleUtil.printMessage(MessageType.ACTIONS_FOR_USER);
            String userResponse = ConsoleUtil.getInput(scan);

            switch (userResponse.toUpperCase()) {
                case "D":
                    sortedByDate();
                    break;
                case "L":
                    getSpaces();
                    break;
                case "B":
                    userInputHandler.createNewBooking(onlineUser);
                    break;
                case "E":
                    userInputHandler.greeting();
                    continueActions = false;
                    break;
                default:
                    ConsoleUtil.printMessage(MessageType.INVALID_COMMAND_ERROR);
                    break;
            }
        }
    }

    /**
     * Приветствует администратора и предоставляет ему варианты действий.
     *
     * @param onlineUser объект пользователя
     */
    private void greetingsForAdmin(User onlineUser) {
        boolean continueActions = true;
        while (continueActions) {
            ConsoleUtil.printMessage(MessageType.ACTIONS_FOR_ADMINISTRATOR);
            String userResponse = ConsoleUtil.getInput(scan);

            switch (userResponse.toUpperCase()) {
                case "D":
                    sortedByDate();
                    break;
                case "I":
                    userInputHandler.createNewIndividualWorkplace();
                    break;
                case "C":
                    userInputHandler.createNewConferenceRoom();
                    break;
                case "L":
                    getSpaces();
                    break;
                case "B":
                    userInputHandler.createNewBooking(onlineUser);
                    break;
                case "E":
                    userInputHandler.greeting();
                    continueActions = false;
                    break;
                default:
                    ConsoleUtil.printMessage(MessageType.INVALID_COMMAND_ERROR);
                    break;
            }
        }
    }

    /**
     * Сортирует рабочие места и конференц-залы по дате.
     */
    private void sortedByDate() {
        ConsoleUtil.printMessage(MessageType.DATE);
        String dateInput = ConsoleUtil.getInput(scan);
        LocalDate date = LocalDate.parse(dateInput, DateTimeFormatter.ISO_LOCAL_DATE);
        coworkingSpace.getIndividualWorkplacesSlotsByDate(date);
        coworkingSpace.getConferenceRoomsSlotsByDate(date);
    }


    /**
     * Выводит список всех рабочих мест и конференц-залов.
     */
    private void getSpaces() {
        coworkingSpace.printSpaces();
    }
}
