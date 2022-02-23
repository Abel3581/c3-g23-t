package com.estore.ecomerce.controller;

import com.estore.ecomerce.dto.ImageProfileRequest;
import com.estore.ecomerce.service.abstraction.IImageProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/imageProfile")
public class ImageProfileController {

    @Autowired
    private IImageProfileService imageProfileService;

    @PostMapping("")
    public ResponseEntity<ImageProfileRequest> addImageProfile(@RequestBody ImageProfileRequest imageProfileRequest){
        ImageProfileRequest imageSaved = imageProfileService.save(imageProfileRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(imageSaved);
    }
}
