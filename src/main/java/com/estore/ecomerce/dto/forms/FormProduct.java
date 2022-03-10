package com.estore.ecomerce.dto.forms;

import java.util.ArrayList;
import java.util.List;

import com.estore.ecomerce.domain.Category;
import com.estore.ecomerce.domain.ImagePost;
import com.estore.ecomerce.domain.ImageProfile;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormProduct {

    private String name;

    private Double price;

    private String description;

    private int stock = 0;

    private String content;

    private double discount = 0.0;

    private List<Category> categories = new ArrayList<Category>();
    private ImageProfile imageProfile;
    private List<ImagePost> imagePost = new ArrayList<ImagePost>();

    private Long clientId;
    
}
