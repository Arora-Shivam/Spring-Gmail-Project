package com.gmail.service;

import com.gmail.exception.UserAlreadyExistException;
import com.gmail.module.User;
import com.gmail.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao userDao;

    @Override
    public boolean addUser(User user) throws UserAlreadyExistException {

        Optional<User> optionalUser = userDao.findById(user.getEmail());

        if(optionalUser.isPresent()){
            throw new UserAlreadyExistException("UserName already exist");
        }else {
            userDao.save(user);
        }

        return true;
    }
}
