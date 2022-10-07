package com.gmail.service;

import com.gmail.exception.PasswordMisMatchException;
import com.gmail.exception.UserAlreadyExistException;
import com.gmail.module.Mail;
import com.gmail.module.MailDto;
import com.gmail.module.User;
import com.gmail.repository.MailDao;
import com.gmail.repository.UserDao;
import com.gmail.util.GetCurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            throw new UserAlreadyExistException("UserName already exist");
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

        userDao.delete(currentUser);

        getCurrentUser.logout();

        return true;
    }

    @Override
	public boolean sentMail(MailDto mailDto) {

        Mail mail = new Mail();


        mail.setSender(getCurrentUser.getCurrentUser());
        mail.setRecievers(mailDto.getRecievers());
        mail.setBody(mailDto.getBody());
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

		
		return true;
	}

    @Override
    public boolean starredMail(int mailId) {

        User currentUser = getCurrentUser.getCurrentUser();

        Optional<Mail> mailOptional = mailDao.findById(mailId);

        currentUser.getStarred().add(mailOptional.get());

        userDao.save(currentUser);

        return true;
    }

    @Override
    public boolean draftMail(Mail mail) {
        User currentUser = getCurrentUser.getCurrentUser();

        mail.setTimeStamp(ZonedDateTime.now());
        mailDao.save(mail);

        currentUser.getDraft().add(mail);

        userDao.save(currentUser);

        return true;

    }

	@Override
	public boolean deleteMail(Mail mail) {		
		
		User currentLogenInUser=getCurrentUser.getCurrentUser();
		
		//If the current user has sent and recieved mail then only we can delete it
		if(mailService.getAllMail().contains(mail)) {
			currentLogenInUser.getTrashMails().add(mail);
			userDao.save(currentLogenInUser);
			return true;
		}	
		else{
			return false;
		}
		
	}

	@Override
	public boolean restoreMail(Mail mail) {
		// TODO Auto-generated method stub
		User currentLogenInUser=getCurrentUser.getCurrentUser();
		
		//If mail exist in trash box only then we can restore
		if(mailService.getDeletedMails().contains(mail)) {
			currentLogenInUser.getTrashMails().remove(mail);
		
			userDao.save(currentLogenInUser);
		
			return true;
		}
		else {
			return false;
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


}
