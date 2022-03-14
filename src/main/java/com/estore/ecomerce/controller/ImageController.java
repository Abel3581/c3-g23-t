package com.estore.ecomerce.controller;

import com.estore.ecomerce.domain.ImagePost;
import com.estore.ecomerce.domain.ImageProfile;
import com.estore.ecomerce.repository.ImageRepository;
import com.estore.ecomerce.service.ImageService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/images")
@Api(value = "Image Controller", description = "Crud basic for images")
public class ImageController {
    private final ImageService imageService;
    private final ImageRepository imageRepository;

    @GetMapping("/profileimage/{id}")
    @ApiOperation(value = "find imageProfile by id", notes = "Return image" )
    public ResponseEntity<Resource> findImageProfileById(@PathVariable(name = "id") String id){
        ImageProfile image = (ImageProfile) imageRepository.findById(id).get();
        return imageService.generateImage(image);
    }

    @GetMapping("/postimages/{id}")
    @ApiOperation(value = "find imagePost by id", notes = "Return image" )
    public ResponseEntity<Resource> findImagePostById(@PathVariable(name = "id") String id){
        ImagePost image = (ImagePost) imageRepository.findById(id).get();
        return imageService.generateImage(image);
    }

}
