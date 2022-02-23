package com.estore.ecomerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ModelListProducts {
    
    private Long id;
    private String title;
    private ModelImage image;
    private double price;
    private double discount;
    private double rating; 

    public ModelListProducts() {
    }
}
