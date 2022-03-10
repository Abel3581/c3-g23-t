package com.estore.ecomerce.dto;

import java.sql.Timestamp;
import java.util.List;

import com.estore.ecomerce.domain.LineProduct;

import org.apache.commons.math3.util.Precision;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModelListReport {
    
    private Timestamp creationDate;
    private int quantity;
    private String nameProduct;
    private Double price;
    private Double total;

    /**
     * @param creationDate the creationDate to set
     */
    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    /**
     * @param nameProduct the nameProduct to set
     */
    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(Double finalPrice) {
        this.price = 0.0;
        this.price = Precision.round(finalPrice,2);
    }

    /**
     * @param total the total to set
     */
    public void setTotal(Double total) {
        this.total = 0.0; 
        this.total = Precision.round(total,2);
    }

}
