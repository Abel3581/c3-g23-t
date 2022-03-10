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

    public CategoryImage findById(Long id);
    
    public List<CategoryImage> listCategoryActive();
    
    public List<CategoryImage> listCategoryInactive();

    void delete(Long id);
}
