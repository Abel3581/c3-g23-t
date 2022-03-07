
package com.estore.ecomerce.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ModelPurchaseReport {    
    private Long id;
    private Integer quantity = 0;    
    private LocalDateTime creationDate = LocalDateTime.now();
}
