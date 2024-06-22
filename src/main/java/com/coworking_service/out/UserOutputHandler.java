package com.coworking_service.out;

import com.coworking_service.Controller;
import com.coworking_service.in.UserInputHandler;
import com.coworking_service.model.CoworkingSpace;
import com.coworking_service.model.User;
import com.coworking_service.model.enums.MessageType;
import com.coworking_service.model.enums.Role;
import com.coworking_service.service.interfaces.UserDirectoryService;
import com.coworking_service.util.ConsoleUtil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class UserOutputHandler {
    private final CoworkingSpace coworkingSpace = new CoworkingSpace();
    private final Scanner scan = new Scanner(System.in);
    private final UserInputHandler userInputHandler;

    public UserOutputHandler(UserDirectoryService userDirectoryService) {
        this.userInputHandler = new UserInputHandler(userDirectoryService);
    }

    public void greetingsForOnlineUser(User onlineUser) {
        coworkingSpace.addConferenceRoom(10);
        coworkingSpace.addIndividualWorkplace();
        coworkingSpace.addIndividualWorkplace();
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
                greetingsForUser(onlineUser);
            case "L":
                getSpaces();
                greetingsForUser(onlineUser);
            case "E":
                userInputHandler.greeting();
            default:
                greetingsForUser(onlineUser);
        }
    }

    private void greetingsForAdmin(User onlineUser) {
        ConsoleUtil.printMessage(MessageType.ACTIONS_FOR_ADMINISTRATOR);
        String userResponse = ConsoleUtil.getInput(scan);

        switch (userResponse.toUpperCase()) {
            case "D":
                sortedByDate();
                greetingsForAdmin(onlineUser);
            case "I":
                createNewIndividualWorkplace();
                greetingsForAdmin(onlineUser);
            case "C":
                createNewConferenceRoom();
                greetingsForAdmin(onlineUser);
            case "L":
                getSpaces();
                greetingsForAdmin(onlineUser);
            case "E":
                userInputHandler.greeting();
            default:
                greetingsForAdmin(onlineUser);
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
}
