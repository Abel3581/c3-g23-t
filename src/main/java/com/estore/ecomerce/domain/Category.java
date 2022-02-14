package com.estore.ecomerce.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @NotEmpty
    @Column(name = "name", nullable = false, updatable = true, unique = true)
    private String name;

    /*Relationsip!!!*/
    @JsonIgnore
    @ManyToMany(mappedBy = "categories")
    private List<Product> products = new ArrayList<Product>();  
}
