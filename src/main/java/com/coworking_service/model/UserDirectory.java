package com.coworking_service.model;

import com.coworking_service.exception.NoSuchUserExistsException;

import java.util.LinkedHashMap;
import java.util.Optional;

public class UserDirectory {
    private final LinkedHashMap<String, User> mapOfUsers = new LinkedHashMap<>();

    public UserDirectory(User user) {
        mapOfUsers.put(user.login(), user);
    }

    public boolean isLoginExist(String login) {
        return mapOfUsers.containsKey(login);
    }

    public User findByLogin(String login) throws NoSuchUserExistsException {
        return Optional.ofNullable(login)
                .map(mapOfUsers::get)
                .orElseThrow(() -> new NoSuchUserExistsException(login));
    }
}
