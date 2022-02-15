package com.estore.ecomerce.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotEmpty;
import javax.persistence.CascadeType;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Category extends Base{
   
    @NotEmpty(message = "Nombre Obligatorio")
    @Column(name = "name", nullable = false, updatable = true, unique = true)
    @Pattern(message = "Solo mayusculas", regexp = "[A-Z]")
    private String name;
    @Column(name = "decription")
    private String description;
 
  
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "categories")
    private List<Product> products;  
}
