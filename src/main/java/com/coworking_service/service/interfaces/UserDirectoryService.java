package com.coworking_service.service.interfaces;

import com.coworking_service.exception.NoSuchUserExistsException;
import com.coworking_service.model.User;
import com.coworking_service.model.UserDirectory;

public interface UserDirectoryService {
    boolean checkIsUserExist(String login);

    User findUserByLogin(String login) throws NoSuchUserExistsException;

    UserDirectory getUserDirectory();
}