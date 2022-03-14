package com.estore.ecomerce.service;

import com.estore.ecomerce.domain.Category;
import com.estore.ecomerce.domain.ImageProfile;
import com.estore.ecomerce.dto.CategoryImage;
import com.estore.ecomerce.dto.CategoryResponse;
import com.estore.ecomerce.mapper.CategoryMapper;
import com.estore.ecomerce.repository.CategoryRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private static final String ERROR_FIND_ID = "No se econtro la categoria";
    private static final String ERROR_CONECTION = "Error al intentar conectar con la BD";
    private static final String ERROR_NOT_LIST_CATEGORY = "No se encontro categorias";
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    @Transactional
    @Override
    public ResponseEntity<?> addCategory(
            CategoryResponse category,
            ImageProfile image) {
        ResponseEntity<?> controlFieldsEmpty = controlFieldsEmpty(category);
        if (controlFieldsEmpty != null) {
            return controlFieldsEmpty;
        }

        category.setImageProfile(image);
        try {
            categoryRepository.save(categoryMapper.categoryDtoEntity(category));
            return new ResponseEntity<>("Category created succesfully!",
                    HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Ups something was wrong..!",
                    HttpStatus.CONFLICT);
        }
    }

    private ResponseEntity<?> controlFieldsEmpty(CategoryResponse category) {
        final ResponseEntity<?> messageFieldsEmpty
                = new ResponseEntity<>("The fields Name can't be empty",
                        HttpStatus.NOT_ACCEPTABLE);
        return (category.getName() == null
                || category.getName().trim().isEmpty()) ? messageFieldsEmpty : null;
    }   

    @Transactional
    @Override
    public ResponseEntity<?> update(Long id, CategoryResponse entity, ImageProfile image) {
        Optional<Category> entityById = categoryRepository.findById(id);
        if (entityById.isPresent()) {
            ResponseEntity<?> controlFieldsEmpty = controlFieldsEmpty(entity);
            if (controlFieldsEmpty != null) {
                return controlFieldsEmpty;
            }
        }
        entity.setImageProfile(image);
        try {
            categoryRepository.save(categoryMapper.categoryDtoEntity(entity));
            return new ResponseEntity<>("Category updated succesfully!",
                    HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Ups something was wrong..!",
                    HttpStatus.CONFLICT);
        }
    }

    @Transactional
    @Override
    public CategoryImage findById(Long id) {
        try {
            Optional<Category> entityById = categoryRepository.findById(id);
            if (entityById.isPresent()) {
                CategoryImage entityResponse = categoryMapper.categoryImageEntityDto(entityById.get());
                return entityResponse;
            } else {
                throw new EntityNotFoundException(ERROR_FIND_ID);
            }
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(ERROR_CONECTION);
        }
    }
   
    @Transactional
    @Override
    public List<CategoryImage> findAll() {
        return listZizeCategory(categoryRepository.findAll());
    }

    @Transactional
    @Override
    public List<CategoryImage> listCategoryActive() {
        return listZizeCategory(categoryRepository.listCategoryActive());
    }

    @Transactional
    @Override
    public List<CategoryImage> listCategoryInactive() {
        return listZizeCategory(categoryRepository.listCategoryInactive());
    }

    public List<CategoryImage> listZizeCategory(List<Category> entities) {
        List<CategoryImage> listResponse = new ArrayList<>();
        if (entities.size() == 0) {
            throw new EntityNotFoundException(ERROR_NOT_LIST_CATEGORY);
        }
        for (Category entity : entities) {
            listResponse.add(categoryMapper.categoryImageEntityDto(entity));
        }
        return listResponse;
    }

    @Transactional
    @Override
    public void delete(Long id) throws EntityNotFoundException {
        Category category = getCategory(id);
        category.setSoftDeleted(true);
        category.setStatus(Boolean.FALSE);
        categoryRepository.save(category);
    }

    private Category getCategory(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isEmpty() || category.get().isSoftDeleted()) {
            throw new EntityNotFoundException(ERROR_FIND_ID);
        }
        return category.get();
    }

}
