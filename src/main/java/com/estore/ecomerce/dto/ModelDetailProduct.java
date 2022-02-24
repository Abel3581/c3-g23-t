package com.estore.ecomerce.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ModelDetailProduct {
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

    public ModelDetailProduct(){}


    
}
