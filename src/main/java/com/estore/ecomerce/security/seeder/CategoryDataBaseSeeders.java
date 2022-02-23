
package com.estore.ecomerce.security.seeder;

import com.estore.ecomerce.domain.Category;
import com.estore.ecomerce.repository.CategoryRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class CategoryDataBaseSeeders {
    private static final String NAME_CATEGORY = "TIENDA";

    @Autowired
    private CategoryRepository categoryRepository ;
   

    @EventListener
    public void seed(ContextRefreshedEvent event) {
        List<Category> category = categoryRepository.findAll();
        if (category.isEmpty()) {
            createCategory();
        }
    }
    
    private void createCategory() {           
           // List <Product> products=new ArrayList<>();
            for (int index = 0; index < 5; index++) {
            Category category = new Category();
            category.setName(NAME_CATEGORY+index);
            category.setDescription("Descripción de "+NAME_CATEGORY+index);
            category.setProducts(null);
            category.setStatus(Boolean.TRUE);            
            categoryRepository.save(category);
        }
    }
  
}
