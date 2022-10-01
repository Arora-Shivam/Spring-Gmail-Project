package com.gmail.service;

import com.gmail.exception.NoMailFound;
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

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

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
            userWithEncoder.setDateOfBirth(user.getDateOfBirth());
            userWithEncoder.setPassword(passwordEncoder.encode(user.getPassword()));
            userWithEncoder.setFirstName(user.getFirstName());
            userWithEncoder.setLastName(user.getLastName());
            userWithEncoder.setMobileNumber(user.getMobileNumber());

            userDao.save(userWithEncoder);
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
		        
		        mail.setRecievers(mailDto.getRecievers());
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
		        currentUser.getStarred().add(mailOptional.get());
		
		        userDao.save(currentUser);
		
		        return true;
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
			if(mailService.getAllMail().contains(mail)) {
				currentLogenInUser.getTrashMails().add(mail);
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
    
    


}
