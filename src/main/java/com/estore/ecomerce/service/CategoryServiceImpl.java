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
    public List<CategoryImage> findAll() {
        try {
            List<CategoryImage> listResponse = new ArrayList<>();
            List<Category> entities = categoryRepository.findAll();
            for (Category entity : entities) {
                listResponse.add(categoryMapper.categoryImageEntityDto(entity));
            }
            return listResponse;
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(ERROR_CONECTION);
        }

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
    public CategoryResponse findById(Long id) {
        try {
            Optional<Category> entityById = categoryRepository.findById(id);
            if (entityById.isPresent()) {
                CategoryResponse entityResponse = categoryMapper.categoryEntityDto(entityById.get());
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
    public List<CategoryResponse> listCategoryActive() {
        try {
            List<CategoryResponse> listResponse = new ArrayList<>();
            List<Category> entities = categoryRepository.listCategoryActive();

            for (Category entity : entities) {
                listResponse.add(categoryMapper.categoryListEntityDto(entity));
            }
            return listResponse;
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(ERROR_CONECTION);
        }
    }

    @Transactional
    @Override
    public List<CategoryResponse> listCategoryInactive() {
        try {
            List<CategoryResponse> listResponse = new ArrayList<>();

            List<Category> entities = categoryRepository.listCategoryInactive();
            for (Category entity : entities) {
                listResponse.add(categoryMapper.categoryEntityDto(entity));
            }
            return listResponse;
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(ERROR_CONECTION);
        }
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
