package com.estore.ecomerce.domain;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@SQLDelete(sql = "UPDATE ImageProfile SET soft_delete = true WHERE id=?")
@Where(clause = "soft_delete = false")
@Entity
@PrimaryKeyJoinColumn(name = "id")
public class ImageProfile extends Image{

}
