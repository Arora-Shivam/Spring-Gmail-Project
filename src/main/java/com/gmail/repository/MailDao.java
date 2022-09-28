package com.gmail.repository;

import com.gmail.module.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gmail.module.Mail;

import java.util.List;

@Repository
public interface MailDao extends JpaRepository<Mail,Integer>{

    List<Mail> findByRecievers(User user);

    List<Mail> findBySender(User user);
}
