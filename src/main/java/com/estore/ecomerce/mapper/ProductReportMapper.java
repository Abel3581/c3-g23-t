
package com.estore.ecomerce.mapper;

import com.estore.ecomerce.domain.Product;
import com.estore.ecomerce.dto.ProductReportResponse;
import org.springframework.stereotype.Component;

@Component
public class ProductReportMapper {
  
     public Product productReportDto2Entity(ProductReportResponse response) {
        Product entity = new Product();       
        
        entity.setId(response.getId());
        entity.setName(response.getName());
        entity.setPrice(response.getPrice());
        entity.setDescription(response.getDescription());
        entity.setStock(response.getStock());
        entity.setContent(response.getContent());
        entity.setRating(response.getRating());
        entity.setDiscount(response.getDiscount());
        entity.setRegistration(response.getRegistration());
        entity.setCategories(response.getCategories());
        entity.setClient(response.getClient());
        entity.setImageProfile(response.getImageProfile());
        entity.setImagePost(response.getImagePost());
        entity.setListReports(response.getListReports());
        return entity;
    }


    public ProductReportResponse productReportEntity2Dto(Product entity) {
        ProductReportResponse dto = new ProductReportResponse();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setPrice(entity.getPrice());
        dto.setDescription(entity.getDescription());
        dto.setStock(entity.getStock());
        dto.setContent(entity.getContent());
        dto.setRating(entity.getRating());
        dto.setDiscount(entity.getDiscount());
        dto.setRegistration(entity.getRegistration());
        dto.setCategories(entity.getCategories());
        dto.setClient(entity.getClient());
        dto.setImageProfile(entity.getImageProfile());
        dto.setImagePost(entity.getImagePost());
        dto.setListReports(entity.getListReports());
        return dto;
    }
    
    
}
