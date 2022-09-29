package com.gmail.service;

import com.gmail.module.Mail;
import com.gmail.module.User;
import com.gmail.repository.MailDao;
import com.gmail.repository.UserDao;
import com.gmail.util.GetCurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MailServiceImpl implements MailService{

    @Autowired
    private GetCurrentUser getCurrentUser;

    @Autowired
    private MailDao mailDao;

    @Override
    public List<Mail> inbox() {

        User user = getCurrentUser.getCurrentUser();

        return mailDao.findByRecievers(user);
    }

    @Override
    public List<Mail> sentBox() {

        User user = getCurrentUser.getCurrentUser();

        List<Mail> draftedMail = user.getDraft();


        List<Mail>  mailSentByUser = user.getSent();

        for(Mail mailDrafted : draftedMail){
            mailSentByUser.remove(mailDrafted);
        }

        return mailSentByUser;
    }

    @Override
    public List<Mail> draftedMail() {

        User user = getCurrentUser.getCurrentUser();

        return user.getDraft();
    }

    @Override
    public List<Mail> getAllMail() {

        User user = getCurrentUser.getCurrentUser();

        Set<Mail> mailSet = new HashSet<>();

        mailSet.addAll(user.getDraft());
        mailSet.addAll(user.getSent());
        mailSet.addAll(user.getStarred());
        mailSet.addAll(inbox());
        mailSet.removeAll(getDeletedMails());
 
        return new ArrayList<>(mailSet);
    }

    @Override
    public List<Mail> searchMail(String keyword) {

        List<Mail> mailList = getAllMail();

        Set<Mail> resultMails = new HashSet<>();
        
        for(Mail res : mailList){
            if(res.getBody().toLowerCase().contains(keyword.toLowerCase()) ||
                    res.getSender().getEmail().toLowerCase().contains(keyword.toLowerCase())){
                resultMails.add(res);
            }
        }

        for(Mail res : mailList){
            List<User> reciever = res.getRecievers();
            for(User user : reciever){
                if(user.getEmail().toLowerCase().contains(keyword.toLowerCase())){
                    resultMails.add(res);
                }
            }
        }

        return new ArrayList<>(resultMails);
    }

	@Override
	public List<Mail> getDeletedMails() {
		// TODO Auto-generated method stub
		
		User currentLogedInUser=getCurrentUser.getCurrentUser();
		
		List<Mail> deletedMails=currentLogedInUser.getTrashMails();
		
		return deletedMails;
	}

	@Override
	public List<Mail> getStarredMails() {
		// TODO Auto-generated method stub
		
		User currentLogedInUser=getCurrentUser.getCurrentUser();
		
		List<Mail> starredMails=currentLogedInUser.getStarred();
		
		return starredMails;
	}
}
