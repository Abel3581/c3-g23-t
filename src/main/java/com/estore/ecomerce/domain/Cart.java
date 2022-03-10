package com.estore.ecomerce.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.estore.ecomerce.utils.enums.EnumState;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ApiModel("Model Cart")
public class Cart {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @ApiModelProperty(value = "the cart id", required = true)
    private Long id;

    @Column(name = "state", nullable = false, updatable = true)
    @Enumerated(value = EnumType.STRING)
    @ApiModelProperty(value = "the cart state", required = true)
    private EnumState enumState;

    @CreationTimestamp
    @Column(name = "registration", updatable = false, nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime registration = LocalDateTime.now();

    //TODO El total no se guarda, crearemos un objeto en dto.

    /*--Relationship--!!*/
    
    //REFERENCIA AL DUEÑO DEL CARRITO
    @ManyToOne(cascade = {}, fetch = FetchType.EAGER)
    @ApiModelProperty(value = "the client id", required = true)
    private Client buyer;

    //REFERENCIA A LA LINEAS DE PRODUCTOS
    @OneToMany(mappedBy = "cart",cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.REFRESH}, orphanRemoval = true)
    private List<LineProduct> lineProducts = new ArrayList<>();
    
}
