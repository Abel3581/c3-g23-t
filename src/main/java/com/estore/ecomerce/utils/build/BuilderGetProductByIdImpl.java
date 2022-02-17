package com.estore.ecomerce.utils.build;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.estore.ecomerce.domain.Category;
import com.estore.ecomerce.domain.Client;
import com.estore.ecomerce.domain.ImagePost;
import com.estore.ecomerce.domain.ImageProfile;
import com.estore.ecomerce.domain.PurchaseReport;
import com.estore.ecomerce.dto.ModelCategory;
import com.estore.ecomerce.dto.ModelClient;
import com.estore.ecomerce.dto.ModelDetailProduct;
import com.estore.ecomerce.dto.ModelImage;

public class BuilderGetProductByIdImpl implements BuilderGetProductById{
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

    public BuilderGetProductByIdImpl setId(Long id){
        this.id = id;
        return this;
    }

    public BuilderGetProductByIdImpl setName(String name){
        this.name = name;
        return this;
    }

    public BuilderGetProductByIdImpl setPrice(Double price, Double discount){
        this.price = price - ((discount/100)*price);
        return this;
    }

    public BuilderGetProductByIdImpl setDescription(String description){
        this.description = description;
        return this;
    }
    public BuilderGetProductByIdImpl setStock(int stock){
        this.stock = stock;
        return this;
    }

    public BuilderGetProductByIdImpl setContent(String content){
        this.content = content;
        return this;
    }

    public BuilderGetProductByIdImpl setRating(double rating){
        this.rating = rating;
        return this;
    }

    public BuilderGetProductByIdImpl setDiscount(double discount){
        this.discount = discount;
        return this;
    }

    public BuilderGetProductByIdImpl setRegistration(LocalDateTime registration){
        this.registration = registration;
        return this;
    }

    public BuilderGetProductByIdImpl setCategories(List<Category> categories){
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

    public BuilderGetProductByIdImpl setClient(Client client){
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

    public BuilderGetProductByIdImpl setImage(ImageProfile image){
        if(image != null){
            String url = "http://localhost:8080/api/v1/images/profileimage/";
            this.imageProfile = new ModelImage(image.getName(), url+image.getId());
        }
        return this;
    }

    public BuilderGetProductByIdImpl setPostImages(List<ImagePost> imagesPost){
        List<ModelImage> imagesRequest = new ArrayList<ModelImage>();
        String url = "http://localhost:8080/api/v1/postimages/";
        if(imagesPost.size() > 0){
            for (ImagePost image : imagesPost) {
                imagesRequest.add(
                    new ModelImage(image.getName(), url+image.getId())
                );
            }
        }
        this.imagesPost = (ArrayList<ModelImage>) imagesRequest;
        return this;
    }

    public BuilderGetProductByIdImpl setQuantitySold(List<PurchaseReport> listReports){

        if(listReports.size() > 0){
            for (PurchaseReport purchase : listReports) {
                this.quantitySold = this.quantitySold + purchase.getQuantity();
            }
        }
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

        return modelDetailProduct;
    }
    
}
