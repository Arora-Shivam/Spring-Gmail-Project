package com.gmail.controller;

import com.gmail.module.User;
import com.gmail.repository.UserDao;
import com.gmail.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> addUser(@RequestBody User user){
        boolean response = userService.addUser(user);
        return new ResponseEntity<>("user added", HttpStatus.ACCEPTED);
    }


}
