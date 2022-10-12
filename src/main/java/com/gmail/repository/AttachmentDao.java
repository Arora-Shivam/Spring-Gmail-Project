package com.gmail.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gmail.module.Attachment;

public interface AttachmentDao extends JpaRepository<Attachment, Integer> {
}
