package com.estore.ecomerce.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Client extends User{
    
    @Column(name = "name", nullable = false, updatable= true)
    private String name;

    @Column(name = "surname", nullable = false, updatable= true)
    private String surname;

    @Column(name = "city", nullable = false, updatable = true)
    private String city;

    @Column(name = "country", nullable = false, updatable = true)
    private String country;

    @Column(name = "state", nullable = false, updatable = true)
    private String state;
    
    /*Relationsip!!!*/
    @OneToOne
    @JoinColumn(name="profile_image")
    private ImageProfile imageProfile;

    @JsonManagedReference
    @OneToMany(mappedBy = "buyer",cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.REFRESH}, orphanRemoval = true)
    private List<Cart> cart = new ArrayList<Cart>();

    @JsonManagedReference
    @OneToMany(mappedBy = "client",cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.REFRESH}, orphanRemoval = true)
    private List<Product> product = new ArrayList<Product>();

}
