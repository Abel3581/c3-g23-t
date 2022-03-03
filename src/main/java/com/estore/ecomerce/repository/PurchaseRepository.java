
package com.estore.ecomerce.repository;

import com.estore.ecomerce.domain.PurchaseReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRepository extends JpaRepository<PurchaseReport, Long>{
    
}
