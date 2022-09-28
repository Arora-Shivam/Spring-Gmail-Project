package com.gmail.util;

import com.gmail.config.SecurityUser;
import com.gmail.module.User;
import com.gmail.repository.UserDao;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Data
public class GetCurrentUser {

    private Object principal;

    @Autowired
    private UserDao userDao;

//    public boolean checkLogin() {
//
//        principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if (principal instanceof UserDetails) {
//            return true;
//        }
//
//        return false;
//
//    }

    public User getCurrentUser() {
        principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            String username = ((UserDetails) principal).getUsername();
            //SecurityUser securityCustomer = (SecurityUser) principal;

            Optional<User> currentUser = userDao.findByEmail(username);


            return currentUser.get();
    }
}
