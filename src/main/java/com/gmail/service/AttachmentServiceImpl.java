package com.gmail.service;


import com.gmail.module.Attachment;

import com.gmail.repository.AttachmentRepository;
import com.gmail.repository.MailDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
public class AttachmentServiceImpl implements AttachmentService {
    @Autowired
    private AttachmentRepository attachmentRepository;
@Autowired
private MailDao mailDao;
    @Override
    public String saveAttachment ( MultipartFile[] file ) throws Exception {
        for (int i = 0; i < file.length; i++) {
            String FileName = StringUtils.cleanPath ( file[i].getOriginalFilename ( ) );
            try {
                if ( FileName.contains ( ".." ) ) {
                    throw new Exception ( "Invalid Path Sequence" + FileName );
                }


                Attachment attachment = new Attachment ( FileName ,
                        file[i].getContentType ( )
                        ,file[i].getBytes ( ) );


//                String downloadURl = ServletUriComponentsBuilder.fromCurrentContextPath ( )
//                        .path ( "/download/" )
//                        .path ( Integer.toString(attachment.getId ( )) )
//                        .toUriString ( );

                attachmentRepository.save ( attachment );


            } catch ( Exception e ) {
                throw new Exception ( "Couldn't save file" + FileName );
            }

        }
        return new String (  "saved all");
    }

    @Override
    public Attachment getAttachment ( String fileId ) throws Exception {
        return attachmentRepository
                .findById ( Integer.valueOf(fileId) )
                .orElseThrow (
                        ( ) -> new Exception ( "File not found with Id: " + fileId ) );
    }
}
