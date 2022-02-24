package com.estore.ecomerce.controller;

import com.estore.ecomerce.domain.ImageProfile;
import com.estore.ecomerce.dto.ImageProfileRequest;
import com.estore.ecomerce.service.FileUploadService;
import com.estore.ecomerce.service.abstraction.IImageProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/imageProfile")
public class ImageProfileController {
    
    @Autowired
    private FileUploadService fileUploadService;

    @PostMapping("")
    public ResponseEntity<ImageProfile> addImageProfile(@RequestBody MultipartFile imageProfileRequest){
        ImageProfile imageSaved = fileUploadService.uploadImageProfileToDB(imageProfileRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(imageSaved);
    }



}
