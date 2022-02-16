package com.estore.ecomerce.service;


import com.estore.ecomerce.domain.Category;
import com.estore.ecomerce.repository.BaseRepository;
import com.estore.ecomerce.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//implementacion de servicios Categoria
@Service
public class CategoryServiceImpl extends BaseServiceImpl<Category, String> implements CategoryService{
    @Autowired 
    private  CategoryRepository categoryRepository;  
    public  CategoryServiceImpl(BaseRepository<Category,String> baseRepository){
        super(baseRepository);
    }
    
}
