package com.estore.ecomerce.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ApiModel("Model LineProduct")
public class LineProduct {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @ApiModelProperty(value = "line-product id", required = true)
    private Long id;

    @Column(name = "amount", nullable = false, updatable = true)
    @ApiModelProperty(value = "line-product amount", required = true)
    private int amount;

    /*Relationsip!!!*/
    @OneToOne(cascade = {})
    @ApiModelProperty(value = "line-product productId", required = true)
    private Product product;

    @ManyToOne(cascade = {})
    @ApiModelProperty(value = "line-product cartId", required = true)
    private Cart cart;

    public LineProduct(int amount, Product product, Cart cart) {
        this.amount = amount;
        this.product = product;
        this.cart = cart;
    }
}
