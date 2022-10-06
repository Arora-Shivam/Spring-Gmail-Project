package com.gmail.service;


import com.gmail.module.Attachment;

import com.gmail.repository.AttachmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AttachmentServiceImpl implements AttachmentService {
    @Autowired
    private AttachmentRepository attachmentRepository;

    @Override
    public Attachment saveAttachment ( MultipartFile file ) throws Exception {
        String FileName = StringUtils.cleanPath ( file.getOriginalFilename ( ) );
        try {
            if ( FileName.contains ( ".." ) ) {
                throw new Exception ( "Invalid Path Sequence" + FileName );
            }


            Attachment attachment = new Attachment ( FileName ,
                    file.getContentType ( )
                    , file.getBytes ( ) );
            return attachmentRepository.save ( attachment );
        } catch ( Exception e ) {
            throw new Exception ( "Couldn't save file" + FileName );
        }

    }


    @Override
    public Attachment getAttachment ( String fileId ) throws Exception {
        return attachmentRepository
                .findById ( fileId )
                .orElseThrow (
                        ( ) -> new Exception ( "File not found with Id: " + fileId ) );
    }
}
