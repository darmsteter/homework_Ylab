package com.coworking_service;

import com.coworking_service.exception.PersistException;
import com.coworking_service.exception.WrongDataException;

public class Main {
    public static void main(String[] args) throws PersistException, WrongDataException {
        new Controller().console();
    }
}