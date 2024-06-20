package com.coworking_service.model.enums;

public enum Commands {
    REGISTRATION("R"),
    LOG_IN("L"),
    START_PAGE("S")
    ;

    private final String command;

    Commands(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
