package com.estore.ecomerce.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;




@Entity
@PrimaryKeyJoinColumn(name = "id")
public class ImagePost extends Image{
    @ManyToOne
    @JoinColumn(name = "image_id")
    Image image;

    public Image getImage() {
        return image;
    }


}
