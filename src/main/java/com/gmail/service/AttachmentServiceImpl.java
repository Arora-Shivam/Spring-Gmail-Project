package com.gmail.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.gmail.module.Attachment;
import com.gmail.module.Content;
import com.gmail.repository.AttachmentDao;
import com.gmail.repository.ContentDao;

@Service
public class AttachmentServiceImpl implements AttachmentService {

	@Autowired
	private ContentDao contentDao;

	@Autowired
	private AttachmentDao attachmentDao;

	@Override
	public Content saveAttachment(MultipartFile[] files) throws Exception {

		Content content = new Content();

		Content res = contentDao.save(content);
		for(MultipartFile file : files) {
			
			Attachment attachment = new Attachment();
			attachment.setFileName(file.getOriginalFilename());
			attachment.setFileType(file.getContentType());
			attachment.setData(file.getBytes());
			attachment.setContent(res);
			res.getAttachments().add(attachment);
			
		}
		return contentDao.save(res);
	}

	@Override
	public Attachment getAttachment(Integer fileId) throws Exception {

		Optional<Attachment> attachment = attachmentDao.findById(fileId);

		return attachment.get();
	}
}
