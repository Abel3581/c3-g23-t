package com.estore.ecomerce.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ModelDetailInvoice {
    private Long id;
    private LocalDateTime registration;
    private Double total; 
    private List<ModelProductInvoice> lineProduct;

    public ModelDetailInvoice(){}
}
