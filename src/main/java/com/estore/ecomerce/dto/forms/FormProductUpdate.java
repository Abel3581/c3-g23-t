package com.estore.ecomerce.dto.forms;

import java.util.ArrayList;
import java.util.List;

import com.estore.ecomerce.domain.Category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormProductUpdate {
    private String name;
    private Double price;
    private String description;
    private int stock = 0;
    private String content;
    private double discount = 0.0;
    private List<Category> categories = new ArrayList<Category>();
    private String imageProfile;
    private List<String> imagePost = new ArrayList<String>();

    private Long clientId;
}
