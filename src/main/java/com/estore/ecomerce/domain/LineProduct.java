package com.estore.ecomerce.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class LineProduct {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount", nullable = false, updatable = true)
    private int amount;

    /*Relationsip!!!*/
    @OneToOne(cascade = {})
    private Product product;

    @JsonBackReference
    @ManyToOne(cascade = {})
    private Cart cart;

    public LineProduct(int amount, Product product, Cart cart) {
        this.amount = amount;
        this.product = product;
        this.cart = cart;
    }
}
