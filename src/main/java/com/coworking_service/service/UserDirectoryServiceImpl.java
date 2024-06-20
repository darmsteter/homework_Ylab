package com.coworking_service.service;

import com.coworking_service.exception.NoSuchUserExistsException;
import com.coworking_service.model.User;
import com.coworking_service.model.UserDirectory;
import com.coworking_service.service.interfaces.UserDirectoryService;

public class UserDirectoryServiceImpl implements UserDirectoryService {

    private final UserDirectory userDirectory;

    public UserDirectoryServiceImpl(UserDirectory userDirectory) {
        this.userDirectory = userDirectory;
    }

    public User findUserByLogin(String login) throws NoSuchUserExistsException {
        return userDirectory.findByLogin(login);
    }

    public boolean checkIsUserExist(String login) {
        return userDirectory.isLoginExist(login);
    }
}
