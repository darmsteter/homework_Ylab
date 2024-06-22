package com.coworking_service.out;

import com.coworking_service.in.UserInputHandler;
import com.coworking_service.model.*;
import com.coworking_service.model.enums.MessageType;
import com.coworking_service.model.enums.Role;
import com.coworking_service.service.interfaces.UserDirectoryService;
import com.coworking_service.util.ConsoleUtil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class UserOutputHandler {
    private final Scanner scan = new Scanner(System.in);
    private final UserInputHandler userInputHandler;
    private final CoworkingSpace coworkingSpace;

    public UserOutputHandler(UserDirectoryService userDirectoryService, CoworkingSpace coworkingSpace) {
        this.userInputHandler = new UserInputHandler(userDirectoryService, coworkingSpace);
        this.coworkingSpace = coworkingSpace;
    }

    public void greetingsForOnlineUser(User onlineUser) {

        if (onlineUser.role().equals(Role.ADMINISTRATOR)) {
            greetingsForAdmin(onlineUser);
        } else {
            greetingsForUser(onlineUser);
        }
    }

    private void greetingsForUser(User onlineUser) {
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
                createNewBooking(onlineUser);
                break;
            case "E":
                userInputHandler.greeting();
                break;
            default:
                ConsoleUtil.printMessage(MessageType.INVALID_COMMAND_ERROR);
                break;
        }
    }

    private void greetingsForAdmin(User onlineUser) {
        ConsoleUtil.printMessage(MessageType.ACTIONS_FOR_ADMINISTRATOR);
        String userResponse = ConsoleUtil.getInput(scan);

        switch (userResponse.toUpperCase()) {
            case "D":
                sortedByDate();
                greetingsForAdmin(onlineUser);
                break;
            case "I":
                createNewIndividualWorkplace();
                greetingsForAdmin(onlineUser);
                break;
            case "C":
                createNewConferenceRoom();
                greetingsForAdmin(onlineUser);
                break;
            case "L":
                getSpaces();
                greetingsForAdmin(onlineUser);
                break;
            case "B":
                createNewBooking(onlineUser);
                break;
            case "E":
                userInputHandler.greeting();
                break;
            default:
                ConsoleUtil.printMessage(MessageType.INVALID_COMMAND_ERROR);
                break;
        }
    }


    private void sortedByDate() {
        ConsoleUtil.printMessage(MessageType.DATE);
        String dateInput = ConsoleUtil.getInput(scan);
        LocalDate date = LocalDate.parse(dateInput, DateTimeFormatter.ISO_LOCAL_DATE);
        coworkingSpace.getIndividualWorkplacesSlotsByDate(date);
        coworkingSpace.getConferenceRoomsSlotsByDate(date);

    }

    private void createNewIndividualWorkplace() {
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

    private void createNewConferenceRoom() {
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

    private void getSpaces() {
        coworkingSpace.printSpaces();
    }

    public void createNewBooking(User onlineUser) {
        System.out.println("На какую дату вы хотите создать бронь? Формат ГГГГ-ММ-ДД");
        String dateInput = ConsoleUtil.getInput(scan);
        LocalDate date = LocalDate.parse(dateInput, DateTimeFormatter.ISO_LOCAL_DATE);
        System.out.println(
                "Вы хотите забронировать Индивидуальное рабочее место (i) или конференц зал(c)?"
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
        greetingsForAdmin(onlineUser);
    }

    private void bookingWorkplace(int workplaceID, Workplace workplace, LocalDate date, User onlineUser) {
        HashMap<IndividualWorkplace, Map<String, Slot>> hashMap = coworkingSpace.getIndividualWorkplaces();
        Map<String, Slot> map = hashMap.get(workplace);
        System.out.println("Введите номер слота, который вы хотите забронировать и количество слотов ");
        int time = scan.nextInt();
        int count = scan.nextInt();

        String[] reservedSlots = coworkingSpace.reserveSlots(workplace, map, workplaceID, date, time, count);

        if (reservedSlots != null) {
            System.out.println("Забронированные слоты:");
            for (String slotDescription : reservedSlots) {
                System.out.println(slotDescription);
            }
            new Booking(onlineUser.login(), workplaceID, date, reservedSlots);
        } else {
            System.out.println("Не удалось зарезервировать слоты.");
        }
    }
}
