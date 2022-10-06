package com.gmail.controller;

import com.gmail.dto.ResponseData;
import com.gmail.module.Attachment;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class AttachmentController {


    @Autowired
    private AttachmentService attachementService;


    @PostMapping("/upload")
    public ResponseEntity<ResponseData> UploadFile( @RequestParam("file") MultipartFile file) throws Exception {
        Attachment attachment = attachementService.saveAttachment(file);
        String downloadURl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(attachment.getId())
                .toUriString();

        return new ResponseEntity<>(new ResponseData(attachment.getFileName(),
                downloadURl,
                file.getContentType(),
                file.getSize()), HttpStatus.OK);
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) throws Exception {
        Attachment attachment = attachementService.getAttachment(fileId);
       return  ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(attachment.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + attachment.getFileName()
                                + "\"")
                .body(new ByteArrayResource(attachment.getData()));
}



}
