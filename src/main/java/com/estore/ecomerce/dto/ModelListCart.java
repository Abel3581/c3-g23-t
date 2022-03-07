package com.estore.ecomerce.dto;

import com.estore.ecomerce.utils.enums.EnumState;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ModelListCart {
    private Long id;
    private EnumState state;
    private int quantity;
    private Double total;
    private String detailCart;

    public ModelListCart(){}

}
