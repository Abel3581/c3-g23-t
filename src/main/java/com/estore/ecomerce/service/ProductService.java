package com.estore.ecomerce.service;

import java.util.ArrayList;

import com.estore.ecomerce.domain.ImagePost;
import com.estore.ecomerce.domain.ImageProfile;
import com.estore.ecomerce.domain.Product;

import org.springframework.http.ResponseEntity;

public interface ProductService {
    ResponseEntity<?> saveProduct(
        Product product,
        ArrayList<ImagePost> postImage,
        ImageProfile image);

    ResponseEntity<?> getProduct();
    ResponseEntity<?> getProductById(Long id);
}
