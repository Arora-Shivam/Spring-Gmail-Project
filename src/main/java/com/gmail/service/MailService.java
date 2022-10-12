package com.gmail.service;

import java.util.List;

import com.gmail.module.Mail;

public interface MailService {

    public List<Mail> inbox();

    public List<Mail> sentBox();

    public List<Mail> draftedMail();

    public List<Mail> getAllMail();

    public List<Mail> searchMail(String keyword);
    
    public List<Mail> getDeletedMails();
    
    public List<Mail> getStarredMails();


}
