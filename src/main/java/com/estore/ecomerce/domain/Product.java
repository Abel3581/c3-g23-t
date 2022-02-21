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

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name", nullable = false, updatable = true)
    private String name;
    
    @Column(name = "price", nullable = false, updatable = true)
    private Double price;

    @Column(name = "description", nullable = false, updatable = true)
    private String description;

    @Column(name = "stock", nullable = false, updatable = true)
    private int stock = 0;
    
    @Column(name = "content", nullable = true, updatable = true)
    private String content;

    @Column(name = "rating", nullable = false, updatable = true)
    private double rating;

    @Column(name = "discount", nullable = true, updatable = true)
    private double discount = 0.0;

    @CreationTimestamp
    @Column(name = "registration", updatable = false, nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime registration = LocalDateTime.now();

    /*Relationsip!!!*/

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
    private Client client;

    @OneToOne(cascade={CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.REFRESH})
    @JoinColumn(name="imageProfile")
    private ImageProfile imageProfile;

    @OneToMany(cascade={CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.REFRESH})
    @JoinColumn(name="imagePost")
    private List<ImagePost> imagePost = new ArrayList<ImagePost>();

    @OneToMany(cascade={CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.REFRESH})
    @JoinColumn(name="listReports")
    private List<PurchaseReport> listReports = new ArrayList<PurchaseReport>();

}
