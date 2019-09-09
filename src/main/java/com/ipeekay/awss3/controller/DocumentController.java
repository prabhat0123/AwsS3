package com.ipeekay.awss3.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ipeekay.awss3.service.DocumentManagementService;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
public class DocumentController {

    @Autowired
    private DocumentManagementService documentManagementService;

    @PostMapping("${app.endpoint.uploadFiles}")
    public void uploadMultipleFiles(@ModelAttribute MultipartFile file) throws IOException {
    	
    	log.debug("Upload multipart endpoint called");
        documentManagementService.normalUploadToS3(file);
    }
    
   
}