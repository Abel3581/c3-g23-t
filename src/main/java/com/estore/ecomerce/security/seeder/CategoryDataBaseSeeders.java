package com.estore.ecomerce.security.seeder;

import com.estore.ecomerce.domain.Category;
import com.estore.ecomerce.domain.ImageProfile;
import com.estore.ecomerce.repository.CategoryRepository;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CategoryDataBaseSeeders {

    private static final String categories[] = {"Calzado", "Indumentaria", "Hogar", "Electro", "Perfume"};

    @Autowired
    private CategoryRepository categoryRepository;

    @EventListener
    public void seed(ContextRefreshedEvent event) throws IOException {
        List<Category> categorylis = categoryRepository.findAll();

        if (categorylis.isEmpty()) {

            String sCarpAct = System.getProperty("user.dir");
            File file = new File(sCarpAct + "/src/main/resources/static/images/");  //carpeta donde se ejecuta codigo

            File[] listFile = file.listFiles();
            for (int index = 0; index < categories.length; index++) {
                ImageProfile image = new ImageProfile();
                Category category = new Category();
                category.setName(categories[index].toUpperCase());
                category.setDescription("Descripción de " + categories[index]);

                InputStream inputStream = new FileInputStream(listFile[index]);
                //String name, @Nullable String originalFilename, @Nullable String contentType, InputStream contentStream)
                MultipartFile partFile = new MockMultipartFile(listFile[index].getName().toString(), listFile[index].getName().toString(), "image/png", inputStream);

                image.setFileData(partFile.getBytes());
                image.setFileType("image/png");
                image.setName(partFile.getName());
                category.setImageProfile(image);
                category.setStatus(Boolean.TRUE);
                category.setProducts(null);
                categoryRepository.save(category);

            }
        }
    }

   
}


