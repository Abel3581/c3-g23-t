
package com.estore.ecomerce.security.seeder;

import com.estore.ecomerce.domain.Category;
import com.estore.ecomerce.domain.ImageProfile;
import com.estore.ecomerce.repository.CategoryRepository;
import java.io.File;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class CategoryDataBaseSeeders {
    private static final String categories[]= {"Calzado", "Indumentaria", "Hogar", "Electro","Perfume"};

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
        String sCarpAct = System.getProperty("user.dir");
        File file = new File(sCarpAct+"/src/main/resources/static/images/");  //carpeta donde se ejecuta codigo
        ImageProfile image=new ImageProfile();
        
        File[] listFile = file.listFiles();
            for (int index = 0; index<categories.length; index++) {
            Category category = new Category();
            category.setName( categories[index].toUpperCase());
            category.setDescription("Descripción de "+categories[index]);
            category.setProducts(null);
            category.setImageProfile((convertFile(listFile[index])));
            category.setStatus(Boolean.TRUE);            
            categoryRepository.save(category);
        }
    }

    private ImageProfile convertFile(File file) {
       
        
            return null;
    }
  
}
