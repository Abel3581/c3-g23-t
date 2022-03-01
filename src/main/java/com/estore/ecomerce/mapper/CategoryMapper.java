package com.estore.ecomerce.mapper;

import com.estore.ecomerce.domain.Category;
import com.estore.ecomerce.domain.ImageProfile;
import com.estore.ecomerce.domain.Product;
import com.estore.ecomerce.dto.CategoryResponse;
import com.estore.ecomerce.service.FileUploadService;
import java.util.ArrayList;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class CategoryMapper {
    
    private FileUploadService fileUploadService;
    
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
       
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setId(category.getId());
        categoryResponse.setName(category.getName());
        categoryResponse.setDescription(category.getDescription());
        categoryResponse.setStatus(category.getStatus());          
        categoryResponse.setProducts(new ArrayList<Product>());
      //  ImageProfile imageProfile = fileUploadService.uploadImageProfileToDB((MultipartFile) category.getImageProfile());

        categoryResponse.setImageProfile(category.getImageProfile());
        return categoryResponse;
    }

    public Category categoryDtoEntityResponse(CategoryResponse response) {
        Category category = new Category();
        category.setId(response.getId());
        category.setName(response.getName());
        category.setDescription(response.getDescription());
        category.setStatus(Boolean.TRUE);
        category.setProducts(response.getProducts());
        return category;
    }

}
