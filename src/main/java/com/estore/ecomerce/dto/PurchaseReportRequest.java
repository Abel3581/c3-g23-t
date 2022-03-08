
package com.estore.ecomerce.dto;

import com.estore.ecomerce.domain.Product;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchaseReportRequest {
    
    private Long id;    
    private Integer quantity = 0;
    private Product product;  
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime creationDate = LocalDateTime.now();
}
