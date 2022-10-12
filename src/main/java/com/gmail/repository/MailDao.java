package com.gmail.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.gmail.module.Mail;
import com.gmail.module.User;

@Repository
public interface MailDao extends JpaRepository<Mail, Integer> {

	List<Mail> findByRecievers(User user);

}
