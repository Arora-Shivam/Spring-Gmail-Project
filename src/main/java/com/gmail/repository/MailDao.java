package com.gmail.repository;

import com.gmail.module.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gmail.module.Mail;

import java.util.List;

@Repository
public interface MailDao extends JpaRepository<Mail,Integer>{

    List<Mail> findByRecievers(User user);

//    @Query("SELECT m FROM Mail m WHERE m.body LIKE %:keyWord%")
//    List<Mail> seachByKeyWord(@Param("keyWord") String keyWord);
    
   

}
