package com.gmail.service;

import com.gmail.exception.UserAlreadyExistException;
import com.gmail.exception.UserNotFoundException;
import com.gmail.module.Mail;
import com.gmail.module.MailDto;
import com.gmail.module.User;

import java.util.List;

public interface UserService {

    public boolean addUser(User user) throws UserAlreadyExistException;

    public boolean deleteUser();
    
    public boolean sentMail(MailDto mailDto);

    public boolean starredMail(int mailId);

    public boolean draftMail(Mail mail);
    
    public boolean deleteMail(Mail mail);
    
    public boolean restoreMail(Mail mail);
}
