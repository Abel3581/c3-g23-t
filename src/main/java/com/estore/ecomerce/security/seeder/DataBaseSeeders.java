package com.estore.ecomerce.security.seeder;

import com.estore.ecomerce.domain.*;
import com.estore.ecomerce.repository.*;
import com.estore.ecomerce.security.ApplicationRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.estore.ecomerce.domain.ImageProfile;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;


import java.util.ArrayList;
import java.util.List;

@Service
public class DataBaseSeeders {

    private static final String PASSWORD = "tienda1234";
    private static final String HOST_EMAIL = "@test.com";
    private static final String DEFAULT_FIRST_NAME = "Tienda";

    //Data Categories
    private static final String categories[] = {"Calzados", "Indumentarias", "Hogar",
    "Electros", "Perfumes", "Deportes","Autos", "Camionetas", "Camiones"};
    private static final String listFile[] = {"image05.png", "image01.png", "image03.png",
    "image02.png", "image04.png", "image06.png",
    "image07.png", "image08.png", "image09.png"};

    //Data Products
    private static final String nameProducts[] = 
    {"Calzado Nike", "Camiseta", "Mesa de madera",
    "Auriculares Sony", "Coco channel", "Cap Hat Adidas",
    "Asiento de bebe", "Puerta de coche", "Adornos camiones",
    "Calzado para mujer","Pantalones","Sillon",
    "Mouse","Perfume cartel","Raqueta",
    "Repuesto de parabrisas","Llavero","Ruedas",
    "Sandalias hawaii","Gorro navideño","Porta retratos"};
    
    private static final Double priceProducts[] = 
    {120.0, 110.0, 328.0,
     200.0, 170.0, 150.0,
     130.0, 110.0, 110.0,
     154.0,80.0,142.0,
     92.0,14.2,315.0,
     720.0,143.2,80.0,
     223.0,100.0,90.0};

     private static final Double discountProducts[] = 
    {20.0, 10.0, 28.0,
     10.0, 70.0, 50.0,
     0.0, 10.0, 10.0,
     14.0,0.0,12.0,
     2.0,14.0,15.0,
     10.0,13.0,8.0,
     90.0,10.0,10.0};

     private static final Double ratingProducts[] = 
    {5.0, 2.1, 2.8,
     3.1, 1.0, 5.0,
     2.2, 2.5, 1.0,
     1.7,1.0,2.0,
     3.3,2.3,3.0,
     4.1,3.8,4.2,
     3.0,3.1,4.0};


    private static final String imageProfileImage[] = 
    {"imageProfile01.png", "imageProfile02.png", "imageProfile03.png",
    "imageProfile04.png", "imageProfile05.png", "imageProfile06.png",
    "imageProfile07.png", "imageProfile08.png", "imageProfile09.png",
    "imageProfile10.png","imageProfile11.png","imageProfile12.png",
    "imageProfile13.png","imageProfile14.png","imageProfile15.png",
    "imageProfile16.png","imageProfile17.png","imageProfile18.png",
    "imageProfile19.png","imageProfile20.png","imageProfile21.png"};
    


    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IRoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private IImageProfileRepository iImageProfileRepository;
    @Autowired
    private ICartRepository cartRepository;
    @Autowired
    private IProductRepository productRepository;
    @Autowired
    private IClientRepository clientRepository;
    @Autowired
    private CategoryRepository categoryRepository;


    @EventListener
    public void seed(ContextRefreshedEvent event) throws IOException {
        List<Role> roles = roleRepository.findAll();
        if (roles.isEmpty()) {
            createRoles();
        }

        List<Client> users = clientRepository.findAll();
        if (users.isEmpty()) {
            createUsers();
        }

        List<Category> categories = categoryRepository.findAll();
        if(categories.isEmpty()){
            createCategories();
        }

        List<Product> products = productRepository.findAll();
        if(products.isEmpty()){
            createProducts();
        }
    }

    private void createRoles() {
        createRole(1L, ApplicationRole.ADMIN);
        createRole(2L, ApplicationRole.USER);
    }

    private void createUsers() {
        createUsers(ApplicationRole.ADMIN);
        createUsers(ApplicationRole.USER);
    }

    private void createUsers(ApplicationRole applicationRole) {
        for (int index = 0; index < 10; index++) {
            Client user = new Client();
            user.setUsername(DEFAULT_FIRST_NAME);
            user.setEmail(applicationRole.getName() + index + HOST_EMAIL);
            user.setPassword(passwordEncoder.encode(PASSWORD));
            user.setCity("Garin");
            user.setCountry("Argentina");
            user.setSurname("Acevedo");
            user.setState("state");
            user.setName(applicationRole.getName() + index);

            List<Role> roles = new ArrayList<>();
            roles.add(roleRepository.findByName(applicationRole.getFullRoleName()));
            user.setRoles(roles);
            clientRepository.save(user);
        }
    }

    private void createRole(Long id, ApplicationRole applicationRole) {
        Role role = new Role();
        role.setId(id);
        role.setName(applicationRole.getFullRoleName());
        role.setDescription(applicationRole.getName());
        roleRepository.save(role);
    }

    private void createCategories() throws IOException{
        String sCarpAct = System.getProperty("user.dir");
            for (int index = 0; index < categories.length; index++) {
                ImageProfile image = new ImageProfile();
                Category category = new Category();
                category.setName(categories[index].toUpperCase());
                category.setDescription("Descripción de " + categories[index]);

                InputStream inputStream = new FileInputStream(new File(sCarpAct + "/src/main/resources/static/images/" + listFile[index]));
                //String name, @Nullable String originalFilename, @Nullable String contentType, InputStream contentStream)
                MultipartFile partFile = new MockMultipartFile(listFile[index], listFile[index], "image/png", inputStream);
                image.setFileData(partFile.getBytes());
                image.setFileType("image/png");
                image.setName(partFile.getName());
                category.setImageProfile(image);
                category.setStatus(Boolean.TRUE);
                category.setProducts(null);
                categoryRepository.save(category);

        }
    }

    private void createProducts() throws IOException{
        System.out.println("Crear productos!");
        int indexCat = 0;
        String sCarpAct = System.getProperty("user.dir");
        List<Category> categories = categoryRepository.findAll();
        User user = userRepository.findByEmail("ADMIN"+"0"+"@test.com");

            for (int index = 0; index < nameProducts.length; index++) {
                ImageProfile imageProfile = new ImageProfile();
                ImagePost imagePost = new ImagePost();
                List<Category> categoriesAdd = new ArrayList<Category>();
                List<ImagePost> imagePostAdd = new ArrayList<ImagePost>();          
                Product product = new Product();
                product.setClient(user);
                product.setName(nameProducts[index]);
                product.setDiscount(discountProducts[index]);
                product.setPrice(priceProducts[index]);
                product.setRegistration(LocalDateTime.now());
                product.setDescription("Descripcion de : "+nameProducts[index]);
                product.setContent("Contenido de : "+nameProducts[index]);
                product.setStock(index*10+10);
                product.setRating(ratingProducts[index]);
                if(indexCat > 8){
                    indexCat = 0;
                }
                categoriesAdd.add(categories.get(indexCat));
                product.setCategories(categoriesAdd);
                
                InputStream inputProfilefile = new FileInputStream(new File(sCarpAct + "/src/main/resources/static/imageProfile/" + imageProfileImage[index]));
                MultipartFile profileFile = new MockMultipartFile(imageProfileImage[index], imageProfileImage[index], "image/png", inputProfilefile);
                
                InputStream inputPostfile = new FileInputStream(new File(sCarpAct + "/src/main/resources/static/imageProfile/" + imageProfileImage[index]));
                MultipartFile postFile = new MockMultipartFile(imageProfileImage[index], imageProfileImage[index], "image/png", inputPostfile);
                

                imageProfile.setFileData(profileFile.getBytes());
                imageProfile.setFileType("image/png");
                imageProfile.setName(profileFile.getName());

                imagePost.setFileData(postFile.getBytes());
                imagePost.setFileType("image/png");
                imagePost.setName(postFile.getName());
                imagePostAdd.add(imagePost);
                product.setImageProfile(imageProfile);
                product.getImagePost().add(imagePost);
                productRepository.save(product);

                indexCat = indexCat + 1 ;
        }
    }

    

}
