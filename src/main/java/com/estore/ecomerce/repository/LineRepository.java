package com.estore.ecomerce.repository;

import com.estore.ecomerce.domain.LineProduct;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LineRepository extends CrudRepository<LineProduct,Long>{
    
}
