package com.estore.ecomerce.domain;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE Client SET soft_delete = true WHERE id=?")
@Where(clause = "soft_delete = false")
@Entity
@PrimaryKeyJoinColumn(name = "id")
@ApiModel("Model Client")
public class Client extends User  {

    @Column(name = "name", nullable = false, updatable= true)
    @ApiModelProperty(value = "the Client name", required = true)
    private String name;

    @Column(name = "surname", nullable = false, updatable= true)
    @ApiModelProperty(value = "the Client surname", required = true)
    private String surname;

    @Column(name = "city", nullable = false, updatable = true)
    @ApiModelProperty(value = "the Client city", required = true)
    private String city;

    @Column(name = "country", nullable = false, updatable = true)
    @ApiModelProperty(value = "the Client country", required = true)
    private String country;

    @Column(name = "state", nullable = false, updatable = true)
    @ApiModelProperty(value = "the Client state", required = true)
    private String state;

    // Constructor para Test de cliente
    public Client(Long id, String username, String password, String email, Timestamp timestamp, boolean softDeleted, List<Role> roles,
                  String name) {
        super(id, username, password, email, timestamp, softDeleted, roles);
    }

    /*Relationsip!!!*/
    @OneToOne
    @JoinColumn(name = "profile_image")
    private ImageProfile imageProfile;

    @JsonManagedReference
    @OneToMany(mappedBy = "buyer", cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.REFRESH}, orphanRemoval = true)
    private List<Cart> cart = new ArrayList<Cart>();

    @JsonManagedReference
    @OneToMany(mappedBy = "client", cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.REFRESH}, orphanRemoval = true)
    private List<Product> product = new ArrayList<Product>();



}