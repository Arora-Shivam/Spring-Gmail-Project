package com.gmail.controller;

import com.gmail.module.Attachment;
import com.gmail.module.Content;
import com.gmail.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class AttachmentController {


    @Autowired
    private AttachmentService attachementService;


    @PostMapping("/upload")
    public ResponseEntity<Content> UploadFile(@RequestParam("file") MultipartFile file) throws Exception {
        Content content = attachementService.saveAttachment(file);


        return new ResponseEntity<>(content, HttpStatus.OK);
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Integer fileId) throws Exception {
        Attachment attachment = attachementService.getAttachment(fileId);
        return  ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(attachment.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + attachment.getFileName()
                                + "\"")
                .body(new ByteArrayResource(attachment.getData()));
    }



}