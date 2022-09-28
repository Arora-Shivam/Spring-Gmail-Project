package com.gmail.service;

import com.gmail.module.Mail;
import com.gmail.module.User;
import com.gmail.repository.MailDao;
import com.gmail.util.GetCurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MailServiceImpl implements MailService{

    @Autowired
    private GetCurrentUser getCurrentUser;

    @Autowired
    private MailDao mailDao;

    @Override
    public List<Mail> inbox() {
        System.out.println("chekc");
        User user = getCurrentUser.getCurrentUser();
        System.out.println("chekc");
        return mailDao.findByRecievers(user);
    }
}
