package com.gmail.service;

import com.gmail.exception.UserAlreadyExistException;
import com.gmail.exception.UserNotFoundException;
import com.gmail.module.Mail;
import com.gmail.module.User;

public interface UserService {

    public boolean addUser(User user) throws UserAlreadyExistException;
    
    public boolean sentMail(Mail mail);
}
