package com.gmail.service;

import com.gmail.exception.UserAlreadyExistException;
import com.gmail.module.User;
import com.gmail.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDao userDao;

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
}
