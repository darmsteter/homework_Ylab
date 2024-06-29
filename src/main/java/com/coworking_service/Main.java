package com.coworking_service;

import com.coworking_service.exception.PersistException;

public class Main {
    public static void main(String[] args) throws PersistException {
        new Controller().console();
    }
}