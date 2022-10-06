package com.gmail.service;

import com.gmail.module.Attachment;

import org.springframework.web.multipart.MultipartFile;

public interface AttachmentService {
    Attachment saveAttachment(MultipartFile file) throws Exception;

    Attachment getAttachment(String fileId)throws Exception;


}
