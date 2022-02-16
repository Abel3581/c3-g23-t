package com.estore.ecomerce.repository;

import com.estore.ecomerce.domain.ImageProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IImageProfileRepository extends JpaRepository<ImageProfile, Long> {
}
