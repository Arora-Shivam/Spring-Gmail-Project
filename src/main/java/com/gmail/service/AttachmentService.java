package com.gmail.service;

import org.springframework.web.multipart.MultipartFile;

import com.gmail.module.Attachment;
import com.gmail.module.Content;

public interface AttachmentService {
	Content saveAttachment(MultipartFile[] file) throws Exception;

	Attachment getAttachment(Integer fileId) throws Exception;

}
