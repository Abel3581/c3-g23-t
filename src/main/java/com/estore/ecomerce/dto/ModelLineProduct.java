package com.estore.ecomerce.dto;

import lombok.Data;

@Data
public class ModelLineProduct {
    private Long idLineProduct;
    private String nameProduct;
    private ModelImage image;
    private double price;
    private int amount;
    private String url; 

    public ModelLineProduct() {
    }

    public ModelLineProduct(Long idLineProduct,String nameProduct, ModelImage image, double price, int amount, String url) {
        this.idLineProduct = idLineProduct;
        this.nameProduct = nameProduct;
        this.image = image;
        this.price = price;
        this.amount = amount;
        this.url = url;
    }
    
    
}
