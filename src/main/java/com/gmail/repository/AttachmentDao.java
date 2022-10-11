package com.gmail.repository;

import com.gmail.module.Attachment;
import com.gmail.module.Content;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentDao extends JpaRepository<Attachment, Integer> {
}
