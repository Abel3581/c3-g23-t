package com.estore.ecomerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ModelLineProduct {
    private String nameProduct;
    private ModelImage image;
    private double price;
    private int amount;
    private String url; 

    public ModelLineProduct() {
    }
    
}
