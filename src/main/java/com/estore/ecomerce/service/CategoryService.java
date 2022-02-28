package com.estore.ecomerce.service;

import com.estore.ecomerce.domain.ImageProfile;
import com.estore.ecomerce.dto.CategoryResponse;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface CategoryService {

    public ResponseEntity<?> addCategory(CategoryResponse category,ImageProfile profileImage );

    public List<CategoryResponse> findAll();

    public CategoryResponse update(Long id, CategoryResponse categoryRequest);

    public CategoryResponse findById(Long id);
    
    public List<CategoryResponse> listCategoryActive();
    
    public List<CategoryResponse> listCategoryInactive();

    void delete(Long id);
}
