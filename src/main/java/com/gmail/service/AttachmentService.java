package com.gmail.service;

import com.gmail.module.Attachment;
import com.gmail.module.Content;
import org.springframework.web.multipart.MultipartFile;

public interface AttachmentService {
    Content saveAttachment(MultipartFile file) throws Exception;

    Attachment getAttachment(Integer fileId)throws Exception;

}
