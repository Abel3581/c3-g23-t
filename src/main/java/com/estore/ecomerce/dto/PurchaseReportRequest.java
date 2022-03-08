
package com.estore.ecomerce.dto;

import com.estore.ecomerce.domain.Product;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Getter
@Setter
public class PurchaseReportRequest {
    
    private Long id;    
    private Integer quantity = 0;
    private Product product;     
    private LocalDateTime creationDate = LocalDateTime.now();
}
