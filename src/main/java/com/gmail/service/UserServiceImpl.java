package com.gmail.service;

import com.gmail.exception.UserAlreadyExistException;
import com.gmail.module.Mail;
import com.gmail.module.User;
import com.gmail.repository.MailDao;
import com.gmail.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public boolean addUser(User user) throws UserAlreadyExistException {

        Optional<User> optionalUser = userDao.findByEmail(user.getEmail());

        if(optionalUser.isPresent()){
            throw new UserAlreadyExistException("UserName already exist");
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
	public boolean sentMail(Mail mail) {
		
		mail.setTimeStamp(ZonedDateTime.now());
		
		System.out.println("Before Mail Save");
		mailDao.save(mail);
		System.out.println("After Mail Save");
		
		Optional<User> optSender=userDao.findByEmail(mail.getSender().getEmail());
		
		User sender=optSender.get();
		
		List<Mail> sentBox=sender.getSent();
		
		sentBox.add(mail);
		
		System.out.println("Before Sender Save");
		
		userDao.save(sender);
		System.out.println("After Sender Save");
		
		List<User> recievers=mail.getRecievers();
		
		for(User reciever : recievers) {
			
			Optional<User> optReciever=userDao.findByEmail(reciever.getEmail());
			
			User recUser=optReciever.get();
			
			recUser.getInbox().add(mail);
			System.out.println("Before Reciever Save");
			userDao.save(recUser);
			System.out.println("After Reciever Save");
			
		}
		
		
		
		return true;
	}
}
