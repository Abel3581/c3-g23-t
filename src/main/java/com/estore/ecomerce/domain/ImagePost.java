package com.estore.ecomerce.domain;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Entity
@PrimaryKeyJoinColumn(name = "id")
public class ImagePost extends Image{
    
}
