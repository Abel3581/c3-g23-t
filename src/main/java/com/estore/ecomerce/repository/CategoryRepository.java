package com.estore.ecomerce.repository;

import com.estore.ecomerce.domain.Category;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<Category,Long>{
    
}
