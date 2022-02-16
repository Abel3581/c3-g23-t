package com.estore.ecomerce.service;

import com.estore.ecomerce.domain.Image;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

public interface ImageService {
    ResponseEntity<Resource> generateImage(Image image);
}
