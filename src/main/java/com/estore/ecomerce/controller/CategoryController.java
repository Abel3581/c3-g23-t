package com.estore.ecomerce.controller;

import com.estore.ecomerce.dto.CategoryResponse;
import com.estore.ecomerce.service.CategoryService;
import com.estore.ecomerce.service.FileUploadService;
import java.net.URISyntaxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/category")
@CrossOrigin("*")
public class CategoryController {

    @Autowired
    private CategoryService service;
    private final FileUploadService fileUploadService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createCategory(
            @RequestPart(value = "image", required = false) MultipartFile image,
            @RequestPart(value = "category", required = true) CategoryResponse category)
            throws URISyntaxException {
           
        ResponseEntity<?> response = service.addCategory(category, fileUploadService.uploadImageProfileToDB(image));
        return new ResponseEntity<>(response.getBody(), response.getStatusCode());
    }

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
    public ResponseEntity<?> update(@PathVariable Long id, 
             @RequestPart(value="image",required=false) MultipartFile image,
             @RequestPart(value="category", required=true) CategoryResponse entity) 
            throws URISyntaxException{
      
        ResponseEntity<?> response = service.update( id,entity,fileUploadService.uploadImageProfileToDB(image));
        return new ResponseEntity<>(response.getBody(), response.getStatusCode());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws EntityNotFoundException {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    @GetMapping("/active")
    public ResponseEntity<?> findAllActive() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.listCategoryActive());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/inactive")
    public ResponseEntity<?> findAllInactive() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.listCategoryInactive());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
