package com.estore.ecomerce.service;


import com.estore.ecomerce.domain.Category;
import com.estore.ecomerce.repository.BaseRepository;
import com.estore.ecomerce.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
//implementacion de servicios Categoria
@Service
public class CategoryServiceImpl extends BaseServiceImpl<Category, Long> implements CategoryService{
    @Autowired 
    private  CategoryRepository categoryRepository;  
    public  CategoryServiceImpl(BaseRepository<Category,Long> baseRepository){
        super(baseRepository);
    }
    
   
    @Transactional
    @Override
    public Category save(Category entity) throws Exception{
         try {
        entity.setName(entity.getName().toUpperCase());
        categoryRepository.save(entity);
       return entity;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        
    }
}
