package com.estore.ecomerce.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("Model Product")
public class Product {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @ApiModelProperty(value = "the product id", required = true)
    private Long id;
    
    @Column(name = "name", nullable = false, updatable = true)
    @ApiModelProperty(value = "the product name", required = true)
    private String name;
    
    @Column(name = "price", nullable = false, updatable = true)
    @ApiModelProperty(value = "the product price", required = true)
    private Double price;

    @Column(name = "description", nullable = false, updatable = true)
    @ApiModelProperty(value = "the product description", required = true)
    private String description;

    @Column(name = "stock", nullable = false, updatable = true)
    @ApiModelProperty(value = "the product stock", required = true)
    private int stock = 0;
    
    @Column(name = "content", nullable = true, updatable = true)
    @ApiModelProperty(value = "the product content", required = true)
    private String content;

    @Column(name = "rating", nullable = false, updatable = true)
    @ApiModelProperty(value = "the product rating", required = true)
    private double rating;

    @Column(name = "discount", nullable = true, updatable = true)
    @ApiModelProperty(value = "the product discount", required = true)
    private double discount = 0.0;

    @CreationTimestamp
    @Column(name = "registration", updatable = false, nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime registration = LocalDateTime.now();

    /*Relationsip!!!*/

    public Product(String name, Double price, String description, int stock, String content, double rating,
            double discount, LocalDateTime registration, List<Category> categories, User client,
            ImageProfile imageProfile, List<ImagePost> imagePost, List<PurchaseReport> listReports) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.stock = stock;
        this.content = content;
        this.rating = rating;
        this.discount = discount;
        this.registration = registration;
        this.categories = categories;
        this.client = client;
        this.imageProfile = imageProfile;
        this.imagePost = imagePost;
        this.listReports = listReports;
    }

    @ManyToMany
    @JoinTable(
    name = "product_whit_category", 
    joinColumns = @JoinColumn(name = "product_id"), 
    inverseJoinColumns = @JoinColumn(name = "category_id"))
    @Column(name = "category", updatable = true, nullable = true)
    private List<Category> categories = new ArrayList<Category>();

    //REFERENCIA AL DUEÑO DEL PRODUCTO
    @JsonBackReference
    @ManyToOne(cascade = {}, fetch = FetchType.EAGER)
    @ApiModelProperty(value = "the product clientId", required = true)
    private User client;

    @OneToOne(cascade={CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.REFRESH})
    @JoinColumn(name="imageProfile")
    @ApiModelProperty(value = "the product imageProfile", required = true)
    private ImageProfile imageProfile;

    @OneToMany(cascade={CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.REFRESH})
    @JoinColumn(name="imagePost")
    @ApiModelProperty(value = "the product imagePost", required = true)
    private List<ImagePost> imagePost = new ArrayList<ImagePost>();

    @OneToMany(cascade={CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.REFRESH})
    @JoinColumn(name="listReports")
    private List<PurchaseReport> listReports = new ArrayList<PurchaseReport>();

}
