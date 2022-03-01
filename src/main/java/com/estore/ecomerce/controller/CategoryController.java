package com.estore.ecomerce.controller;

import com.estore.ecomerce.domain.ImagePost;
import com.estore.ecomerce.domain.ImageProfile;
import com.estore.ecomerce.dto.CategoryRequest;
import com.estore.ecomerce.dto.CategoryResponse;
import com.estore.ecomerce.dto.forms.FormProduct;
import com.estore.ecomerce.service.CategoryService;
import com.estore.ecomerce.service.FileUploadService;
import com.estore.ecomerce.service.ImageService;
import java.net.URISyntaxException;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/category")
public class CategoryController {

    @Autowired
    private CategoryService service;
    private final FileUploadService fileUploadService;
    private final ImageService imageService;
   
    
    @PostMapping(consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createCategory(
        @RequestPart(value="image",required=false) MultipartFile image,            
        @RequestPart(value="category", required=true) CategoryResponse category)
    throws URISyntaxException{        
       //mapea imagen
        ImageProfile profileImage = fileUploadService.uploadImageProfileToDB(image);
        
        ResponseEntity<?> response = service.addCategory(category,profileImage);
                                    

        return new ResponseEntity<>(response.getBody(), response.getStatusCode());
    }
    
    
    

//    @PostMapping("")
//    public ResponseEntity<?> save(@RequestBody CategoryRequest entity) {
//        try {
//            return ResponseEntity.status(HttpStatus.OK).body(service.addCategory(entity));
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
//        }
//    }

    @GetMapping("")
    public ResponseEntity<?> findAll() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.findById(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody CategoryResponse entity) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.update(id, entity));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id)throws EntityNotFoundException {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

     @GetMapping("/active")
    public ResponseEntity<?> findAllActive() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.listCategoryActive());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }
    @GetMapping("/inactive")
    public ResponseEntity<?> findAllInactive() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.listCategoryInactive());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }
}

