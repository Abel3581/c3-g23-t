package com.estore.ecomerce.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.estore.ecomerce.utils.enums.EnumState;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ModelDetailCart {
    private Long id;
    private EnumState enumState;
    private LocalDateTime registration;
    private ModelClient client;
    private String invoice;
    private String optCloseCart;
    private String optCleanCart;
    private List<ModelLineProduct> lineProduct;
    private double total;
    
    public ModelDetailCart(){}
}
