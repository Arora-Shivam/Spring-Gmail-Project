package com.gmail.service;

import com.gmail.module.Attachment;
import com.gmail.module.Content;
import com.gmail.repository.AttachmentDao;
import com.gmail.repository.ContentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.Optional;

@Service
public class AttachmentServiceImpl implements AttachmentService {

    @Autowired
    private ContentDao contentDao;

    @Autowired
    private AttachmentDao attachmentDao;

    @Override
    public Content saveAttachment(MultipartFile file) throws Exception {

        Content content = new Content();

        Content res = contentDao.save(content);


        Attachment attachment = new Attachment();
        attachment.setFileName(file.getOriginalFilename());
        attachment.setFileType(file.getContentType());
        attachment.setData(file.getBytes());
//        attachment.setContent(res);

        //Attachment savedAttachment = attachmentDao.save(attachment);
        res.getAttachments().add(attachment);

        return contentDao.save(res);
    }

    @Override
    public Attachment getAttachment(Integer fileId) throws Exception {

        Optional<Attachment> attachment = attachmentDao.findById(fileId);

        return attachment.get();
    }
}
