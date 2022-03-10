package com.estore.ecomerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ModelProductInvoice {
    private String nameProduct;
    private int amount;
    private Double price = 0.0;

    public ModelProductInvoice(){}

}
