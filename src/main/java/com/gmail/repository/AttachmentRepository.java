package com.gmail.repository;

import com.gmail.module.Attachment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentRepository extends JpaRepository < Attachment, String > {
}
