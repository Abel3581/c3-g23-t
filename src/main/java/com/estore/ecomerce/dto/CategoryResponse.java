
package com.estore.ecomerce.dto;

import com.estore.ecomerce.domain.Category;
import com.estore.ecomerce.domain.ImageProfile;
import com.estore.ecomerce.domain.Product;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse {
    private Long id;
    private String name;  
    private String description;
    private Boolean status;
    private List<Product> products; 
    private ImageProfile imageProfile;
}
