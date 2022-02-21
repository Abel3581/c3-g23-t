package com.estore.ecomerce.service;
import java.util.List;

import com.estore.ecomerce.domain.Image;
import com.estore.ecomerce.domain.ImagePost;
import com.estore.ecomerce.domain.Product;
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

    @Override
    public void deleteImageByProduct(ResponseEntity<?> request) {
        if(request.getStatusCodeValue() == 200){
            Product product = (Product) request.getBody();

            if(product.getImageProfile() != null) 
            deleteImage(product.getImageProfile());
    
            if(product.getImagePost().size() > 0) 
            deleteImagePost(product.getImagePost());
        }
    }

    private void deleteImage(Image image){imageRepository.delete(image);}
    
    private void deleteImagePost(List<ImagePost> images){
        for (ImagePost image : images){deleteImage(image);}
    }

}
