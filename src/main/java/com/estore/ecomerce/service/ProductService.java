package com.estore.ecomerce.service;

import java.util.ArrayList;

import com.estore.ecomerce.domain.ImagePost;
import com.estore.ecomerce.domain.ImageProfile;
import com.estore.ecomerce.dto.forms.FormProduct;

import org.springframework.http.ResponseEntity;

public interface ProductService {
    ResponseEntity<?> saveProduct(
        FormProduct product,
        ArrayList<ImagePost> postImage,
        ImageProfile image);

    ResponseEntity<?> updateProduct(
        
        FormProduct product,
        Long id,
        ArrayList<ImagePost> postImage,
        ImageProfile image);

    ResponseEntity<?> getProduct();
    ResponseEntity<?> getDetailProductById(Long id);
    ResponseEntity<?> getProductByCategory(Long id);
    ResponseEntity<?> getProductById(Long id);
    ResponseEntity<?> deleteProduct(Long id);
}
