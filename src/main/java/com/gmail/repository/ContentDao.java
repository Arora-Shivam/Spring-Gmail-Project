package com.gmail.repository;

import com.gmail.module.Content;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentDao extends JpaRepository<Content, Integer> {
}
