package com.estore.ecomerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ModelLineProduct {
    private Long idLineProduct;
    private Long idCart;
    private String nameProduct;
    private ModelImage image;
    private double price;
    private int amount;
    private String updateAmountProduct;
    private String clearProduct;
    private String url; 

    public ModelLineProduct() {
    }

    public ModelLineProduct(Long idCart,Long idLineProduct,String nameProduct, ModelImage image, double price, int amount, String url) {
        this.idLineProduct = idLineProduct;
        this.nameProduct = nameProduct;
        this.image = image;
        this.price = price;
        this.amount = amount;
        this.url = url;
    }
    
    
}
