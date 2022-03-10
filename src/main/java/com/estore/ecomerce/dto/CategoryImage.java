
package com.estore.ecomerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryImage {
    private Long id;
    private String name;  
    private String description;
    private Boolean status;  
    private ModelImage image;
}
