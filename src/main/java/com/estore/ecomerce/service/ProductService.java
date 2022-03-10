package com.estore.ecomerce.service;

import java.util.ArrayList;

import com.estore.ecomerce.domain.Client;
import com.estore.ecomerce.domain.ImagePost;
import com.estore.ecomerce.domain.ImageProfile;
import com.estore.ecomerce.domain.User;
import com.estore.ecomerce.dto.forms.FormProduct;

import org.springframework.http.ResponseEntity;

public interface ProductService {
    ResponseEntity<?> saveProduct(
        Client client,
        FormProduct product,
        ArrayList<ImagePost> postImage,
        ImageProfile image);

    ResponseEntity<?> updateProduct(
        
        FormProduct product,
        Long id,
        ArrayList<ImagePost> postImage,
        ImageProfile image);

    ResponseEntity<?> getProduct(Client client);
    ResponseEntity<?> getAllProducts(Long idCategory, Double price, String name);
    ResponseEntity<?> getDetailProductById(Long id, Client client);
    ResponseEntity<?> getProduct();
    ResponseEntity<?> getDetailProductById(Long id);
    ResponseEntity<?> getProductByCategory(Long id);
    ResponseEntity<?> getProductsPopularsByCategory(Long id);
    ResponseEntity<?> getProductById(Long id);
    ResponseEntity<?> deleteProduct(Long id);
    ResponseEntity<?> getProductById(Long id, User user);
}
