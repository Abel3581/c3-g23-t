package com.estore.ecomerce.repository;

import com.estore.ecomerce.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IClientRepository extends JpaRepository<Client, Long> {
    Client findByEmail(String username);
}
