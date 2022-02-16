package com.estore.ecomerce.service;
import com.estore.ecomerce.domain.Image;
import com.estore.ecomerce.repository.ImageRepository;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService{
    private final ImageRepository imageRepository;

    @Override
    public ResponseEntity<Resource> generateImage(Image image) {
        return ResponseEntity.ok()
               .contentType(MediaType.parseMediaType(image.getFileType()))
               .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename = " + image.getName())
               .body(new ByteArrayResource(image.getFileData()));
    }
}
