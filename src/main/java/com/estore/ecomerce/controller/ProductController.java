package com.estore.ecomerce.controller;

import java.net.URISyntaxException;
import java.util.ArrayList;

import com.estore.ecomerce.domain.Client;
import com.estore.ecomerce.domain.ImagePost;
import com.estore.ecomerce.domain.ImageProfile;
import com.estore.ecomerce.dto.forms.FormProduct;
import com.estore.ecomerce.service.FileUploadService;
import com.estore.ecomerce.service.ImageService;
import com.estore.ecomerce.service.ProductService;
import com.estore.ecomerce.service.abstraction.IUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/products")
@Api(value = "Product Controller", description = "Crud for products")
public class ProductController {
    private final ProductService productService;
    private final IUserService userService; 
    private final FileUploadService fileUploadService;
    private final ImageService imageService;

    @PostMapping(consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "Create Product", notes = "Return Product" )
    public ResponseEntity<?> createProduct(
        @RequestPart(value="profileimage",required=false) MultipartFile image,
        @RequestPart(value="postimages",required=false)  ArrayList<MultipartFile> postImage,
        @RequestPart(value="product", required=true) FormProduct product)
    throws URISyntaxException, NotFoundException{
        Client client = (Client) userService.getInfoUser();
        
        ArrayList<ImagePost> postImages = fileUploadService.uploadImagePostToDB(postImage);
        ImageProfile profileImage = fileUploadService.uploadImageProfileToDB(image);
        
        ResponseEntity<?> response = productService
                                    .saveProduct(client, product,postImages,profileImage);

        return new ResponseEntity<>(response.getBody(), response.getStatusCode());
    }

    @GetMapping("/all")
    @ApiOperation(value = "Get All Products", notes = "Return Products" )
    public ResponseEntity<?> getAllProducts(
        @RequestParam(value="category", required = false) Long idCategory,
        @RequestParam(value="price", required = false) Double price,
        @RequestParam(value="name", required = false) String name
    ){
        ResponseEntity<?> response = productService.getAllProducts(idCategory,price,name);
        return new ResponseEntity<>(response.getBody(), response.getStatusCode());
    }

    
    @GetMapping("/me")
    @ApiOperation(value = "Get Product client", notes = "Return Product client" )
    public ResponseEntity<?> getProducts() throws NotFoundException{
        Client client = (Client) userService.getInfoUser();
        ResponseEntity<?> response = productService.getProduct(client);
        return new ResponseEntity<>(response.getBody(), response.getStatusCode());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get Product for by id", notes = "Return Product" )
    public ResponseEntity<?> getProductById(@PathVariable(name = "id") Long id) throws NotFoundException{
        try {
            Client client = (Client) userService.getInfoUser();    
        } catch (Exception e) {
            System.out.println("Client is null");
        }
        Client client = null;
        ResponseEntity<?> response = productService.getDetailProductById(id, client);
        return new ResponseEntity<>(response.getBody(), response.getStatusCode());
    }

    @GetMapping("/{id}/reports")
    @ApiOperation(value = "Get report by id Product", notes = "Return Product" )
    public ResponseEntity<?> getReportsByIdProduct(@PathVariable(name = "id") Long id) throws NotFoundException{
        Client client = (Client) userService.getInfoUser();
       
        ResponseEntity<?> response = productService.getReportsByIdProduct(client, id);
        return new ResponseEntity<>(response.getBody(), response.getStatusCode());
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deleted Product", notes = "Return Product" )
    public ResponseEntity<?> deleteProduct(@PathVariable(name = "id") Long id) throws NotFoundException{
        Client client = (Client) userService.getInfoUser();
        
        if(productService.getProductById(id,client).getStatusCodeValue() == 200){
            imageService.deleteImageByProduct(productService.getProductById(id));
            ResponseEntity<?> response = productService.deleteProduct(id);
            return new ResponseEntity<>(response.getBody(), response.getStatusCode());
        }else{
            return new ResponseEntity<>("Product not exists", HttpStatus.FORBIDDEN);
        }
        
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Updated Product", notes = "Return Product updated" )
    public ResponseEntity<?> updateProduct(@PathVariable(name = "id") Long id,
        @RequestPart(value="profileimage",required=false) MultipartFile image,
        @RequestPart(value="postimages",required=false)  ArrayList<MultipartFile> postImage,
        @RequestPart(value="product", required=true) FormProduct product)
        throws URISyntaxException, NotFoundException{
        
        Client client = (Client) userService.getInfoUser();

        if(productService.getProductById(id,client).getStatusCodeValue() == 200){
            ArrayList<ImagePost> postImages = fileUploadService.updateImagesPostToDB(postImage);
            ImageProfile profileImage = fileUploadService.uploadImageProfileToDB(image);
    
            ResponseEntity<?> response = productService.updateProduct(product, id, postImages, profileImage);
            return new ResponseEntity<>(response.getBody(), response.getStatusCode());
        }else{
            return new ResponseEntity<>("Product not exists", HttpStatus.FORBIDDEN);
        }
    }
    

}
