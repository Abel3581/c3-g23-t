package com.estore.ecomerce.utils.build;
import com.estore.ecomerce.domain.ImageProfile;
import com.estore.ecomerce.dto.ModelImage;
import com.estore.ecomerce.dto.ModelListProducts;


public class BuilderGetProductsImpl implements BuilderGetProducts{
    
    private Long id;
    private String title;
    private ModelImage image;
    private double price;
    private double discount;
    private double rating;


    public BuilderGetProductsImpl setId(Long id){
        this.id = id;
        return this;
    }

    public BuilderGetProductsImpl setTitle(String title){
        this.title = title;
        return this;
    }
    
    public BuilderGetProductsImpl setPrice(double price, double discount){
        this.price = (price - ((discount/100)*price));
        return this;
    }

    public BuilderGetProductsImpl setDiscount(double discount){
        this.discount = discount;
        return this;
    }

    public BuilderGetProductsImpl setRating(double rating){
        this.rating = rating;
        return this;
    }

    public BuilderGetProductsImpl setImage(ImageProfile image){
        if(image != null){
            String url = "http://localhost:8080/api/v1/image/";
            this.image = new ModelImage(image.getName(), url+image.getId());
        }
        return this;
    }



    @Override
    public ModelListProducts ModelListProducts() {
        ModelListProducts modelListProducts = new ModelListProducts();
        modelListProducts.setId(this.id);
        modelListProducts.setTitle(this.title);
        modelListProducts.setRating(this.rating);
        modelListProducts.setDiscount(this.discount);
        modelListProducts.setPrice(this.price);
        modelListProducts.setImage(this.image);
        return modelListProducts;
    }
    
}
