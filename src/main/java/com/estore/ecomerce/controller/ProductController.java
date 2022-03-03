package com.estore.ecomerce.controller;

import java.net.URISyntaxException;
import java.util.ArrayList;

import com.estore.ecomerce.domain.ImagePost;
import com.estore.ecomerce.domain.ImageProfile;
import com.estore.ecomerce.dto.forms.FormProduct;
import com.estore.ecomerce.dto.forms.ProductDetailResponse;
import com.estore.ecomerce.service.FileUploadService;
import com.estore.ecomerce.service.ImageService;
import com.estore.ecomerce.service.ProductService;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;
    private final FileUploadService fileUploadService;
    private final ImageService imageService;

    @PostMapping(consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createProduct(
        @RequestPart(value="profileimage",required=false) MultipartFile image,
        @RequestPart(value="postimages",required=false)  ArrayList<MultipartFile> postImage,
        @RequestPart(value="product", required=true) FormProduct product)
    throws URISyntaxException{
        ArrayList<ImagePost> postImages = fileUploadService.uploadImagePostToDB(postImage);
        ImageProfile profileImage = fileUploadService.uploadImageProfileToDB(image);
        
        ResponseEntity<?> response = productService
                                    .saveProduct(product,postImages,profileImage);

        return new ResponseEntity<>(response.getBody(), response.getStatusCode());
    }

    
    @GetMapping
    public ResponseEntity<?> getProducts(){
        ResponseEntity<?> response = productService.getProduct();
        return new ResponseEntity<>(response.getBody(), response.getStatusCode());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable(name = "id") Long id){
        ResponseEntity<?> response = productService.getDetailProductById(id);
        return new ResponseEntity<>(response.getBody(), response.getStatusCode());
    }

    @GetMapping("/category")
    public ResponseEntity<?> getProductByCategory(@RequestParam(value="category", required = false) Long idCategory){
        ResponseEntity<?> response = productService.getProductByCategory(idCategory);
        return new ResponseEntity<>(response.getBody(), response.getStatusCode());
    }

    @GetMapping("popular/category")
    public ResponseEntity<?> getProductsPopularsByCategory(@RequestParam(value="category", required = false) Long idCategory){
        ResponseEntity<?> response = productService.getProductsPopularsByCategory(idCategory);
        return new ResponseEntity<>(response.getBody(), response.getStatusCode());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable(name = "id") Long id){
        imageService.deleteImageByProduct(productService.getProductById(id));
       
        ResponseEntity<?> response = productService.deleteProduct(id);
        return new ResponseEntity<>(response.getBody(), response.getStatusCode());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable(name = "id") Long id,
        @RequestPart(value="profileimage",required=false) MultipartFile image,
        @RequestPart(value="postimages",required=false)  ArrayList<MultipartFile> postImage,
        @RequestPart(value="product", required=true) FormProduct product)
        throws URISyntaxException{
        
        ArrayList<ImagePost> postImages = fileUploadService.updateImagesPostToDB(postImage);
        ImageProfile profileImage = fileUploadService.uploadImageProfileToDB(image);

        ResponseEntity<?> response = productService.updateProduct(product, id, postImages, profileImage);
        return new ResponseEntity<>(response.getBody(), response.getStatusCode());
    }
    

}
