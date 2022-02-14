package com.estore.ecomerce.domain;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@PrimaryKeyJoinColumn(name = "id")
public class ImageProfile extends Image{
    
}
