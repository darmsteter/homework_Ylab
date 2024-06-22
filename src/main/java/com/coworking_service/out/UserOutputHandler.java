package com.coworking_service.out;

import com.coworking_service.Controller;
import com.coworking_service.model.CoworkingSpace;
import com.coworking_service.model.User;
import com.coworking_service.model.enums.MessageType;
import com.coworking_service.model.enums.Role;
import com.coworking_service.util.ConsoleUtil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class UserOutputHandler {
    private final CoworkingSpace coworkingSpace = new CoworkingSpace();
    private final Scanner scan = new Scanner(System.in);

    public void greetingsForOnlineUser(User onlineUser) {
        if (onlineUser.role().equals(Role.ADMINISTRATOR)) {
            greetingsForAdmin(onlineUser);
        } else {
            greetingsForUser(onlineUser);
        }
    }

    public void greetingsForUser(User onlineUser) {
        ConsoleUtil.printMessage(MessageType.ACTIONS_FOR_USER);
        String userResponse = ConsoleUtil.getInput(scan);

        switch (userResponse.toUpperCase()) {
            case "D":
                sortedByDate();
                break;
        }
    }

    private void greetingsForAdmin(User onlineUser) {
        ConsoleUtil.printMessage(MessageType.ACTIONS_FOR_ADMINISTRATOR);
        String userResponse = ConsoleUtil.getInput(scan);

        switch (userResponse.toUpperCase()) {
            case "D":
                sortedByDate();
                break;
            case "I":
                createNewIndividualWorkplace();
                break;
            case "C":
                createNewConferenceRoom();
                break;
        }
    }

    private void sortedByDate() {
        coworkingSpace.addConferenceRoom(10);
        coworkingSpace.addIndividualWorkplace();
        coworkingSpace.addIndividualWorkplace();

        ConsoleUtil.printMessage(MessageType.DATE);
        String dateInput = ConsoleUtil.getInput(scan);
        LocalDate date = LocalDate.parse(dateInput, DateTimeFormatter.ISO_LOCAL_DATE);
        System.out.println(coworkingSpace.getIndividualWorkplacesSlotsByDate(date));
        System.out.println(coworkingSpace.getConferenceRoomsSlotsByDate(date));

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
}
