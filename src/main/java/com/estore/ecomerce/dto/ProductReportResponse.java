
package com.estore.ecomerce.dto;

import com.estore.ecomerce.domain.Category;
import com.estore.ecomerce.domain.ImagePost;
import com.estore.ecomerce.domain.ImageProfile;
import com.estore.ecomerce.domain.PurchaseReport;
import com.estore.ecomerce.domain.User;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor  
public class ProductReportResponse {

    private Long id;   
    private String name;  
    private Double price;
    private String description;
    private int stock = 0;   
    private String content;
    private double rating;
    private double discount = 0.0;
    private LocalDateTime registration = LocalDateTime.now();
    private List<Category> categories = new ArrayList<Category>();
    private User client;
    private ImageProfile imageProfile;
    private List<ImagePost> imagePost = new ArrayList<ImagePost>();
    private List<PurchaseReport> listReports = new ArrayList<PurchaseReport>();
}
