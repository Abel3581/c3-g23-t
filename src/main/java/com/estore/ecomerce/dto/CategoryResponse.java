
package com.estore.ecomerce.dto;

import com.estore.ecomerce.domain.Product;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
@Getter @Setter
public class CategoryResponse {
    private Long id;
    private String name;  
    private String description;
    private Boolean status;
    private List<Product> products; 
}
