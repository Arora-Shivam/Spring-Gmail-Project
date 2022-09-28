package com.gmail.service;

import com.gmail.exception.UserAlreadyExistException;
import com.gmail.module.Mail;
import com.gmail.module.User;

import java.util.List;

public interface MailService {

    public List<Mail> inbox();

    public List<Mail> sentBox();

    public List<Mail> draftedMail();

    public List<Mail> getAllMail();
}
