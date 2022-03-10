package com.estore.ecomerce.utils.build;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.estore.ecomerce.domain.Category;
import com.estore.ecomerce.domain.Client;
import com.estore.ecomerce.domain.ImagePost;
import com.estore.ecomerce.domain.ImageProfile;
import com.estore.ecomerce.domain.Product;
import com.estore.ecomerce.domain.PurchaseReport;
import com.estore.ecomerce.dto.ModelCategory;
import com.estore.ecomerce.dto.ModelClient;
import com.estore.ecomerce.dto.ModelDetailProduct;
import com.estore.ecomerce.dto.ModelImage;

import org.apache.commons.math3.util.Precision;

public class BuilderGetProductByIdNoLogued implements BuilderGetProductById{
    private Long id;
    private String name;
    private Double price;
    private String description;
    private int stock;
    private String content;
    private double rating;
    private double discount;
    private LocalDateTime registration;
    private List<ModelCategory> categories;
    private ModelClient client;
    private ModelImage imageProfile;
    private ArrayList<ModelImage> imagesPost;
    private int quantitySold;
    private String optDeleteUpdateProduct; 
    private String setOptCreateUpdateCartWithProduct;

    public BuilderGetProductByIdNoLogued setId(Long id){
        this.id = id;
        return this;
    }

    public BuilderGetProductByIdNoLogued setName(String name){
        this.name = name;
        return this;
    }

    public BuilderGetProductByIdNoLogued setPrice(Double price, Double discount){
        Double finalPrice = (price - ((discount/100)*price)); 
        this.price = Precision.round(finalPrice,2);
        return this;
    }

    public BuilderGetProductByIdNoLogued setDescription(String description){
        this.description = description;
        return this;
    }
    public BuilderGetProductByIdNoLogued setStock(int stock){
        this.stock = stock;
        return this;
    }

    public BuilderGetProductByIdNoLogued setContent(String content){
        this.content = content;
        return this;
    }

    public BuilderGetProductByIdNoLogued setRating(double rating){
        this.rating = rating;
        return this;
    }

    public BuilderGetProductByIdNoLogued setDiscount(double discount){
        this.discount = discount;
        return this;
    }

    public BuilderGetProductByIdNoLogued setRegistration(LocalDateTime registration){
        this.registration = registration;
        return this;
    }

    public BuilderGetProductByIdNoLogued setCategories(List<Category> categories){
        List<ModelCategory> categoriesRequest = new ArrayList<ModelCategory>();
        String url = "http://localhost:8080/api/v1/category/";
        if(categories.size() > 0){
            for (Category category : categories) {
                categoriesRequest.add(
                    new ModelCategory(url+category.getId(),category.getName())
                );
            }
        }
        this.categories = categoriesRequest;
        return this;
    }

    public BuilderGetProductByIdNoLogued setClient(Client client){
        if(client != null){
            String url = "http://localhost:8080/api/v1/client/";
            ModelClient clientRequest = new ModelClient(url+client.getId(), 
                                                    client.getName());
            this.client = clientRequest;
        }else{
            this.client = null;
        }
        
        return this;
    }

    public BuilderGetProductByIdNoLogued setImage(ImageProfile image){
        if(image != null){
            String url = "http://localhost:8080/api/v1/images/profileimage/";
            this.imageProfile = new ModelImage(image.getId(),image.getName(), url+image.getId());
        }
        return this;
    }

    public BuilderGetProductByIdNoLogued setPostImages(List<ImagePost> imagesPost){
        List<ModelImage> imagesRequest = new ArrayList<ModelImage>();
        String url = "http://localhost:8080/api/v1/images/postimages/";
        if(imagesPost.size() > 0){
            for (ImagePost image : imagesPost) {
                imagesRequest.add(
                    new ModelImage(image.getId(),image.getName(), url+image.getId())
                );
            }
        }
        this.imagesPost = (ArrayList<ModelImage>) imagesRequest;
        return this;
    }

    public BuilderGetProductByIdNoLogued setQuantitySold(List<PurchaseReport> listReports){

        if(listReports != null){
            for (PurchaseReport purchase : listReports) {
                this.quantitySold = this.quantitySold + purchase.getQuantity();
            }
        }
        return this;
    }

    public BuilderGetProductByIdNoLogued setOptDeleteProduct(){
        this.optDeleteUpdateProduct = null;
        return this;
    }

    public BuilderGetProductByIdNoLogued setOptCreateUpdateCartWithProduct(){
    
        this.setOptCreateUpdateCartWithProduct = null;
        return this;
    }


    @Override
    public ModelDetailProduct modelDetailProduct() {
        ModelDetailProduct modelDetailProduct = new ModelDetailProduct();

        modelDetailProduct.setId(this.id);
        modelDetailProduct.setName(this.name);
        modelDetailProduct.setPrice(this.price);
        modelDetailProduct.setDescription(this.description);
        modelDetailProduct.setStock(this.stock);
        modelDetailProduct.setContent(this.content);
        modelDetailProduct.setRating(this.rating);
        modelDetailProduct.setDiscount(this.discount);
        modelDetailProduct.setRegistration(this.registration);
        modelDetailProduct.setCategories(this.categories);
        modelDetailProduct.setClient(this.client);
        modelDetailProduct.setImageProfile(this.imageProfile);
        modelDetailProduct.setImagesPost(this.imagesPost);
        modelDetailProduct.setQuantitySold(this.quantitySold);
        modelDetailProduct.setSetOptCreateUpdateCartWithProduct(this.setOptCreateUpdateCartWithProduct);
        modelDetailProduct.setOptDeleteUpdateProduct(this.optDeleteUpdateProduct);
        return modelDetailProduct;
    }


    
    
}
