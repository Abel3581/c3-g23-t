package com.estore.ecomerce.mapper;

import com.estore.ecomerce.domain.Category;
import com.estore.ecomerce.domain.Product;
import com.estore.ecomerce.dto.CategoryImage;
import com.estore.ecomerce.dto.CategoryResponse;
import com.estore.ecomerce.dto.ModelImage;
import java.util.ArrayList;
import org.springframework.stereotype.Component;


@Component
public class CategoryMapper {
    
  
    //uso

    public Category categoryDtoEntity(CategoryResponse request)  {
        Category category = new Category();
        category.setId(request.getId());
        category.setName(request.getName().toUpperCase());
        category.setDescription(request.getDescription());
        category.setStatus(Boolean.TRUE);
        category.setProducts(request.getProducts());
        category.setImageProfile(request.getImageProfile());
        return category;
    }

    public CategoryResponse categoryEntityDto(Category category) {
       //uso
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setId(category.getId());
        categoryResponse.setName(category.getName());
        categoryResponse.setDescription(category.getDescription());
        categoryResponse.setStatus(category.getStatus());          
        categoryResponse.setProducts(new ArrayList<Product>());
        categoryResponse.setImageProfile(category.getImageProfile());        
        return categoryResponse;
    }
    public CategoryResponse categoryListEntityDto(Category category) {
       //uso
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setId(category.getId());
        categoryResponse.setName(category.getName());
        categoryResponse.setDescription(category.getDescription());
        categoryResponse.setStatus(category.getStatus()); 
        categoryResponse.setImageProfile(category.getImageProfile());        
        return categoryResponse;
    }
    //uso
    public CategoryImage categoryImageEntityDto(Category category){
            String url="http://localhost:8080/api/v1/images/profileimage/";
            CategoryImage categoryImageRespose=new CategoryImage();
            categoryImageRespose.setId(category.getId());
            categoryImageRespose.setDescription(category.getDescription());
            categoryImageRespose.setName(category.getName());
            categoryImageRespose.setStatus(category.getStatus());      
           
            ModelImage modelImage=new ModelImage();
            modelImage.setImageName(category.getImageProfile().getName());           
            modelImage.setUrlImage(url+category.getImageProfile().getId());//      
            categoryImageRespose.setImage(modelImage);
        return categoryImageRespose;
    }
 

}
