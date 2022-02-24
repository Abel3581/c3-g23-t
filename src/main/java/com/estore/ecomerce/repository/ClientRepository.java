package com.estore.ecomerce.repository;

import com.estore.ecomerce.domain.Client;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends CrudRepository<Client,Long>{
    
}
