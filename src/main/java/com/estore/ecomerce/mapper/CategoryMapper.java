package com.estore.ecomerce.mapper;

import com.estore.ecomerce.domain.Category;
import com.estore.ecomerce.dto.CategoryRequest;
import com.estore.ecomerce.dto.CategoryResponse;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category categoryDtoEntity(CategoryRequest request) {
        Category category = new Category();
        category.setId(request.getId());
        category.setName(request.getName().toUpperCase());
        category.setDescription(request.getDescription());
        category.setStatus(Boolean.TRUE);
        category.setProducts(request.getProducts());
        return category;
    }

    public CategoryResponse categoryEntityDto(Category category) {
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setId(category.getId());
        categoryResponse.setName(category.getName());
        categoryResponse.setDescription(category.getDescription());
        categoryResponse.setStatus(category.getStatus());
        categoryResponse.setProducts(category.getProducts());
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
