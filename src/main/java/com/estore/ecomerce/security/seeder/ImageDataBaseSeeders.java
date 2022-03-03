
package com.estore.ecomerce.security.seeder;


import com.estore.ecomerce.domain.ImageProfile;
import com.estore.ecomerce.repository.IImageProfileRepository;
import com.estore.ecomerce.service.FileUploadServiceImpl;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Service
public class ImageDataBaseSeeders  implements WebMvcConfigurer{
//   
    @Autowired
    private IImageProfileRepository imageRepository ;
    private FileUploadServiceImpl uploadSeed;
    @EventListener
    public void seedImage(ContextRefreshedEvent event) {
        List<ImageProfile> listImage = imageRepository.findAll();
        if (!listImage.isEmpty()) {
            createImage();
        }
    }

//   
//    images_default.png   /home/gabriel/NetBeansProjects/c3-g23-t/src/main/resources/static/images/images_default.png
    private void createImage() {
//       String sCarpAct = System.getProperty("user.dir");
//        File carpeta = new File(sCarpAct+"/src/main/resources/static/images/");  //carpeta donde se ejecuta codigo
//        ImageProfile image=new ImageProfile();
//        
//        File[] archivosListado = carpeta.listFiles();
//        
//        for (int i=0; i< archivosListado.length; i++) {
//        File archivo = archivosListado[i];
//        System.out.println(archivo.getName()+"     "+archivo.length()+"     "+archivo.getAbsolutePath());        
//        }
//         // MultipartFile a = (MultipartFile) archivosListado[0];
//         image.setName(archivosListado[0].getName() );
//         
//        
//         System.out.println(image.getName().toUpperCase()+"     " );
       //  System.out.println(a.getName()+"     "+"Funcionaaaaaaaaaaaaa");
    }
  
}
