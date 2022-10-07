package com.gmail.service;


import com.gmail.exception.NoMailFound;

import com.gmail.exception.PasswordMisMatchException;

import com.gmail.exception.UserAlreadyExistException;
import com.gmail.exception.UserNotFoundException;
import com.gmail.module.Mail;
import com.gmail.module.MailDto;
import com.gmail.module.User;
import com.gmail.repository.MailDao;
import com.gmail.repository.UserDao;
import com.gmail.util.GetCurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDao userDao;
    
    @Autowired
    private MailDao mailDao;

    @Autowired
    private GetCurrentUser getCurrentUser;
    
    @Autowired
    private MailService mailService;

    @Override
    public boolean addUser(User user) throws UserAlreadyExistException {

        Optional<User> optionalUser = userDao.findByEmail(user.getEmail());

        if(optionalUser.isPresent()){
            throw new UserAlreadyExistException("Email already exist with the id "+ user.getEmail());
        }else {
            User userWithEncoder = new User();

            userWithEncoder.setEmail(user.getEmail());
            userWithEncoder.setRole(user.getRole());

            if(!checkAge(user.getDateOfBirth())){
                throw new IllegalArgumentException("Age should be atlease 18");
            }

            userWithEncoder.setDateOfBirth(user.getDateOfBirth());

            if(!isValidPassword(user.getPassword())){
                throw new PasswordMisMatchException("Please follow password rules");
            }
            userWithEncoder.setPassword(passwordEncoder.encode(user.getPassword()));
            userWithEncoder.setFirstName(user.getFirstName());
            userWithEncoder.setLastName(user.getLastName());
            userWithEncoder.setMobileNumber(user.getMobileNumber());

            System.out.println(userWithEncoder);

            userDao.save(userWithEncoder);
            System.out.println("check");
        }

        return true;
    }

    @Override
    public boolean deleteUser() {
        User currentUser = getCurrentUser.getCurrentUser();
        if(currentUser==null) {
        	throw new UsernameNotFoundException("User session expired, Please Login Again");
        }
        else {
//        	currentUser.setSent(null);
//        	currentUser.setStarred(null);
//        	currentUser.setTrashMails(null);
//        	currentUser.s
	        userDao.delete(currentUser);
	
	        getCurrentUser.logout();
	
	        return true;
        }
        
    }

    @Override
	public boolean sentMail(MailDto mailDto) {

        Mail mail = new Mail();

        User currentSender =getCurrentUser.getCurrentUser();
        
       if(currentSender==null) {
    	   throw new UserNotFoundException("User session expired, Please Login Again");
       }
       else {
	        
	        	mail.setSender(currentSender);
		        
//	        	this.checkIfUserExist(mailDto.getRecievers());
//		        mail.setRecievers(mailDto.getRecievers());
	        	
//	        	for(User user :mailDto.getRecievers()) {
//	        		try {
//		        		if(checkIfUserExist(user))
//		        			mail.getRecievers().add(user);
//	        		}
//	        		catch(Exception e) {
//	        			System.out.println(e.getMessage());
//	        		}
//	        	}
	        	
	        	List<User> validUsers=mailDto.getRecievers().stream().filter((u1)->this.checkIfUserExist(u1)).collect(Collectors.toList());
		        
	        	mail.setRecievers(validUsers);
	        	
		        mail.setBody(mailDto.getBody());
				mail.setTimeStamp(ZonedDateTime.now());
				
				System.out.println("Before Mail Save");
				System.out.println(mailDto.getRecievers());
				mailDao.save(mail);
				System.out.println("After Mail Save");
				
				Optional<User> optSender=userDao.findByEmail(mail.getSender().getEmail());
		
				User sender=optSender.get();
		
				List<Mail> sentBox=sender.getSent();
		
				sentBox.add(mail);
		
				System.out.println("Before Sender Save");
				
				userDao.save(sender);
				System.out.println("After Sender Save");
		
				
				return true;
			
    	    
       }
	}

    @Override
    public boolean starredMail(int mailId) {
    	
    	
        User currentUser = getCurrentUser.getCurrentUser();
        if(currentUser==null) {
        	throw new UsernameNotFoundException("User session expired, Please Login Again");
        }
        else {
	        Optional<Mail> mailOptional = mailDao.findById(mailId);
	        if(mailOptional.isPresent()) {
	        	if(currentUser.getStarred().contains(mailOptional.get())) {
	        		currentUser.getStarred().remove(mailOptional.get());
	        		userDao.save(currentUser);
	        		return false;
	        	}
	        	else {
		        currentUser.getStarred().add(mailOptional.get());
		
		        userDao.save(currentUser);
		
		        return true;
	        	}
	        }
	        else {
	        	throw new NoMailFound("Mail Does not Exist");
	        }
        }
    }

    @Override
    public boolean draftMail(Mail mail) {
        
    	User currentUser = getCurrentUser.getCurrentUser();
        
        if(currentUser==null) {
        	throw new UsernameNotFoundException("User session expired, Please Login Again");
        }
        else {
	        mail.setTimeStamp(ZonedDateTime.now());
	        mailDao.save(mail);
	
	        currentUser.getDraft().add(mail);
	
	        userDao.save(currentUser);
	
	        return true;
        }

    }

	@Override
	public boolean deleteMail(Mail mail) {		
		
		User currentLogenInUser=getCurrentUser.getCurrentUser();
		
		
		if(currentLogenInUser==null) {
        	throw new UsernameNotFoundException("User session expired, Please Login Again");
        }
        else {
        	//If the current user has sent and recieved mail then only we can delete it
        	System.out.println("Mail before added to trash");
        
        	
        	List<Mail> allMails=new ArrayList<>();
        	allMails.addAll(currentLogenInUser.getSent());
        	allMails.addAll(currentLogenInUser.getDraft());
        	
        	allMails.addAll(mailDao.findByRecievers(currentLogenInUser));
        	
			if(allMails.contains(mail)) {
				currentLogenInUser.getTrashMails().add(mail);
				System.out.println("Mail added to Trash");
				userDao.save(currentLogenInUser);
				return true;
			}	
			else{
				throw new NoMailFound("Mail Does not Exist");
			}
        }
		
	}

	@Override
	public boolean restoreMail(Mail mail) {
		// TODO Auto-generated method stub
		User currentLogenInUser=getCurrentUser.getCurrentUser();
		if(currentLogenInUser==null) {
        	throw new UsernameNotFoundException("User session expired, Please Login Again");
        }
        else {
			//If mail exist in trash box only then we can restore
			if(mailService.getDeletedMails().contains(mail)) {
				currentLogenInUser.getTrashMails().remove(mail);
			
				userDao.save(currentLogenInUser);
			
				return true;
			}
			else {
				throw new NoMailFound("Mail Does not Exist in Trsh Box");
			}
        }
	}

    @Override
    public boolean isValidPassword(String password) {
        if(password == null){
            return false;
        }

        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\S+$).{8,20}$";
        Pattern p = Pattern.compile(regex);

        Matcher m = p.matcher(password);

        return m.matches();
    }

    public boolean checkAge(LocalDate date){

        LocalDate today = LocalDate.now();

        if(today.getYear()-date.getYear() >= 18 ) {
            return true;
        }
        return false;
    }
    
    public boolean checkIfUserExist(User user) {
    	
    	
    		if(!userDao.findByEmail(user.getEmail()).isPresent()) {
    			throw new UserNotFoundException("User with email "+user.getEmail()+" does not exist");
    		}
    	
    	
    	return true;
    }

}
