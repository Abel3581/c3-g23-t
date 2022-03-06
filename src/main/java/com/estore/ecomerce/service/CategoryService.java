package com.estore.ecomerce.service;

import com.estore.ecomerce.domain.ImageProfile;
import com.estore.ecomerce.dto.CategoryImage;
import com.estore.ecomerce.dto.CategoryResponse;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface CategoryService {

    public ResponseEntity<?> addCategory(CategoryResponse category,ImageProfile profileImage );

    public List<CategoryImage> findAll();

    public ResponseEntity<?> update(Long id, CategoryResponse category,ImageProfile profileImage);

    public CategoryResponse findById(Long id);
    
    public List<CategoryResponse> listCategoryActive();
    
    public List<CategoryResponse> listCategoryInactive();

    void delete(Long id);
}
