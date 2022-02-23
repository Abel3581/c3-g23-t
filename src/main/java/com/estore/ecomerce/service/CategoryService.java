package com.estore.ecomerce.service;

import com.estore.ecomerce.dto.CategoryRequest;
import com.estore.ecomerce.dto.CategoryResponse;
import java.util.List;

public interface CategoryService {

    public CategoryResponse addCategory(CategoryRequest categoryResquest);

    public List<CategoryResponse> findAll();

    public CategoryResponse update(Long id, CategoryRequest categoryRequest);

    public CategoryResponse deleteCategory(Long id);

    public CategoryResponse findById(Long id);
    
    public List<CategoryResponse> listCategoryActive();
    
    public List<CategoryResponse> listCategoryInactive();

}
