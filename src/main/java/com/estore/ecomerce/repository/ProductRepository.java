package com.estore.ecomerce.repository;

import java.util.ArrayList;

import com.estore.ecomerce.domain.Product;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product,Long>{
    ArrayList<Product> findByNameContainingIgnoreCase(String name);
}
