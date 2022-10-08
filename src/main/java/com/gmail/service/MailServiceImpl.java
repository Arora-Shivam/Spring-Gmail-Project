package com.gmail.service;

import com.gmail.exception.NoMailFound;
import com.gmail.exception.UserNotFoundException;
import com.gmail.module.Mail;
import com.gmail.module.User;
import com.gmail.repository.MailDao;
import com.gmail.repository.UserDao;
import com.gmail.util.GetCurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
        
        if(user==null) {
        	throw new UsernameNotFoundException("User session expired, Please Login Again");
        }
        else {

			return mailDao.findByRecievers(user);
//	        if(mailDao.findByRecievers(user).size() != 0){
//
//	        }
//
//	        throw new NoMailFound("Your Inbox is Empty");
        }
    }

    @Override
    public List<Mail> sentBox() {

        User user = getCurrentUser.getCurrentUser();
        
        if(user==null) {
        	throw new UsernameNotFoundException("User session expired, Please Login Again");
        }
        else {
	        List<Mail> draftedMail = user.getDraft();
	
	
	        List<Mail>  mailSentByUser = user.getSent();
	
	        for(Mail mailDrafted : draftedMail){
	            mailSentByUser.remove(mailDrafted);
	        }
	        	mailSentByUser.removeAll(user.getTrashMails());

			return mailSentByUser;
//	        if(mailSentByUser.size() != 0){
//
//	        }
//
//	        throw new NoMailFound("Your Sent Box is Empty");
        }
    }

    @Override
    public List<Mail> draftedMail() {

        User user = getCurrentUser.getCurrentUser();

        if(user==null) {
        	throw new UsernameNotFoundException("User session expired, Please Login Again");
        }
        else {

			return user.getDraft();
//	        if(user.getDraft().size() != 0){
//
//	        }
//
//	        throw new NoMailFound("Your Draft is Empty");
        }
    }

    @Override
    public List<Mail> getAllMail() {

        User user = getCurrentUser.getCurrentUser();
        
        if(user==null) {
        	throw new UsernameNotFoundException("User session expired, Please Login Again");
        }
        else {
	        Set<Mail> mailSet = new HashSet<>();
	        
	        
	        
	        try {
	        		if(user.getDraft().size()>0)
	        			mailSet.addAll(user.getDraft());
	    	        if(user.getSent().size()>0)
	    	        	mailSet.addAll(user.getSent());
	    	        if(user.getStarred().size()>0)
	    	        	mailSet.addAll(user.getStarred());
	    	        	
	    	        	mailSet.addAll(inbox());
	    	        
	    	        System.out.println("All ");
	    	        	mailSet.removeAll(user.getTrashMails());
	    	        
			} catch (Exception e) {
				// TODO: handle exception
			}
	        
	 
	        List<Mail> allMail = new ArrayList<>(mailSet);

			return allMail;
//	        if(allMail.size() != 0){
//
//	        }
//
//	        throw new NoMailFound("You Dont Have Any Mails");
        }


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

        List<Mail> allMail = new ArrayList<>(resultMails);

		return allMail;
//        if(allMail.size() != 0){
//
//        }
//
//        throw new NoMailFound("You Dont Have Any Mails");
    }

	@Override
	public List<Mail> getDeletedMails() {
		// TODO Auto-generated method stub
		
		User currentLogedInUser=getCurrentUser.getCurrentUser();
		
		if(currentLogedInUser==null) {
        	throw new UsernameNotFoundException("User session expired, Please Login Again");
        }
        else {
			List<Mail> deletedMails=currentLogedInUser.getTrashMails();


			return deletedMails;
//			if(deletedMails.size()==0) {
//				throw new NoMailFound("Trash is empty");
//			}
//			else {
//
//			}
        }
	}

	@Override
	public List<Mail> getStarredMails() {
		// TODO Auto-generated method stub
		
		User currentLogedInUser=getCurrentUser.getCurrentUser();
		if(currentLogedInUser==null) {
        	throw new UsernameNotFoundException("User session expired, Please Login Again");
        }
        else {
	        	
					List<Mail> starredMails=currentLogedInUser.getStarred();
					try {
					starredMails.removeAll(getDeletedMails());
					}
					catch(NoMailFound n) {
						
					}
					return starredMails;
//						if(starredMails.size()==0) {
//							throw new NoMailFound("You have not Starred any mails yet");
//						}
//						else {
//
//
//						}
			        
		      
		}
	}


}
