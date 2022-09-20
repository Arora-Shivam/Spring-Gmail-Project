package com.gmail.repository;

import com.gmail.module.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserDao extends JpaRepository<User,String> {

    @Override
    Optional<User> findById(String s);
}
