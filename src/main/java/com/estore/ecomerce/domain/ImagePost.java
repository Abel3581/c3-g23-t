package com.estore.ecomerce.domain;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;


@Entity
@PrimaryKeyJoinColumn(name = "id")
public class ImagePost extends Image{
    public ImagePost(){}

    public ImagePost(String originalFilename, String contentType, byte[] bytes) {
        this.setName(originalFilename);
        this.setFileType(contentType);
        this.setFileData(bytes);
    }

}
