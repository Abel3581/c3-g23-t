
package com.estore.ecomerce.dto;

import com.estore.ecomerce.domain.Product;
import java.sql.Timestamp;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchaseReportRequest {
    
    private Long id;    
    private Integer quantity;
    private Product product; 
  
    private Timestamp creationDate ;
}
