package com.estore.ecomerce.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.estore.ecomerce.domain.*;
import com.estore.ecomerce.dto.ModelDetailProduct;
import com.estore.ecomerce.dto.ModelListProducts;
import com.estore.ecomerce.dto.forms.FormProduct;
import com.estore.ecomerce.repository.CartRepository;
import com.estore.ecomerce.repository.CategoryRepository;
import com.estore.ecomerce.repository.ClientRepository;
import com.estore.ecomerce.repository.ImageRepository;
import com.estore.ecomerce.repository.ProductRepository;
import com.estore.ecomerce.security.ApplicationRole;
import com.estore.ecomerce.service.abstraction.IUserService;
import com.estore.ecomerce.utils.build.BuilderGetProductByIdImpl;
import com.estore.ecomerce.utils.build.BuilderGetProductByIdNoLogued;
import com.estore.ecomerce.utils.build.BuilderGetProductsImpl;
import com.estore.ecomerce.utils.enums.EnumState;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{
    
    private final ProductRepository productRepository;
    private final ClientRepository clientRepository;
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;
    private final CartRepository cartRepository;
    @Autowired
    private IUserService userService;

    @Override
    public ResponseEntity<?> saveProduct(Client client, FormProduct product,
                               ArrayList<ImagePost> postImage,
                               ImageProfile image) {
        ResponseEntity<?> controlFieldsEmpty = controlFieldsEmpty(product);
        if(controlFieldsEmpty != null) return controlFieldsEmpty;

        ResponseEntity<?> controlFieldsWithDoubleAndInteger = 
        controlFieldsWithDoubleAndInteger(product);

        if(controlFieldsWithDoubleAndInteger != null) 
        return controlFieldsWithDoubleAndInteger;
        
        ResponseEntity<?> controlCategories = controlCategories(product);
        if(controlCategories != null) return controlCategories;
        
    
        product.setImageProfile(image);          
        product.setImagePost(postImage);

        try {
            productRepository.save(constructorProduct(product));
            return new ResponseEntity<>(product, 
            HttpStatus.OK);    
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Ups something was wrong..!", 
            HttpStatus.CONFLICT);
        }                      
    }

    private ResponseEntity<?> controlFieldsWithDoubleAndInteger(FormProduct product){
        final ResponseEntity<?> messageValueDiscountInvalid =
        new ResponseEntity<>("The field discount is'nt acceptable", 
        HttpStatus.NOT_ACCEPTABLE);

        final ResponseEntity<?> messageValuePriceInvalid =
        new ResponseEntity<>("The field price is'nt acceptable", 
        HttpStatus.NOT_ACCEPTABLE);

        final ResponseEntity<?> messageValueStockInvalid =
        new ResponseEntity<>("The field stock is'nt acceptable", 
        HttpStatus.NOT_ACCEPTABLE);

        if(product.getDiscount() > 100.0 || product.getDiscount() < 0.00)
        return messageValueDiscountInvalid;
        
        if(product.getPrice() < 0.00)
        return messageValuePriceInvalid;

        if(product.getStock() < 0)
        return messageValueStockInvalid; 

        return null; //No hay errores!
    }


    private ResponseEntity<?> controlCategories(FormProduct product){
        final ResponseEntity<?> messageCategoryNotExists =
        new ResponseEntity<>("One category or more is not exists", 
        HttpStatus.NOT_FOUND);

        List<Category> categories = (List<Category>) categoryRepository.findAll();

        if(product.getCategories().size() == 0) return null;
        if(categories.size() == 0 && product.getCategories().size() > 0) 
        return messageCategoryNotExists;

        //Comparamos la lista que se envió de cateogrias con la lista valida.
        return (getCategoriesValids(product.getCategories()).size() 
                == product.getCategories().size())? null:messageCategoryNotExists;


    }

    private List<Category> getCategoriesValids(List<Category> categoriesRequest){
        Predicate<Category> condition = new Predicate<Category>(){
            @Override
            public boolean test(Category category){
                if(categoryRepository.findById(category.getId()).isPresent()){
                    //Si la categoria existe
                    return true;
                }
                    //Si no existe
                    return false;
            }
        };

        categoriesRequest = categoriesRequest.stream()
        .filter(condition)
        .collect(Collectors.toList());
        
        return categoriesRequest;
    }

    private boolean controlClient(Product product){
        Optional<Client> client = clientRepository.findById(product.getClient().getId());
        
        if(client.isPresent()){
            product.setClient(client.get());
            client.get().getProduct().add(product);
            return false;
        }else{
            return true;
        }
    }

    private Product constructorProduct(FormProduct productForm) throws NotFoundException{
        Product product = new Product();
        User client = userService.getInfoUser();
        product.setCategories(productForm.getCategories());
        product.setClient(client);
        product.setName(productForm.getName());
        product.setContent(productForm.getContent());
        product.setCategories(returnCategories(product));
        product.setPrice(productForm.getPrice());
        product.setDiscount(productForm.getDiscount());
        product.setDescription(productForm.getDescription());
        product.setImageProfile(productForm.getImageProfile());
        product.setImagePost(productForm.getImagePost());
        product.setStock(productForm.getStock());
        product.setRating(0.0);  
        return product;
    }

    private List<Category> returnCategories(Product product){
        /*Mapeamos los ids de las categorias por los objetos categoria*/
        List<Category> categoriesReturn = new ArrayList<Category>();
        for (Category element : product.getCategories()) {
            Optional<Category> cat = categoryRepository.findById(element.getId());
            categoriesReturn.add(cat.get());
            //Añadir producto a la lista de categoria
            cat.get().getProducts().add(product);
        }
        return categoriesReturn;
    }

    private ResponseEntity<?> controlFieldsEmpty(FormProduct product){
        final ResponseEntity<?> messageFieldsEmpty = 
        new ResponseEntity<>("The fields Name, Price or Description can't be empty", 
        HttpStatus.NOT_ACCEPTABLE); 

        return (
            product.getName() == null || 
            product.getName().trim().isEmpty() &&
            product.getPrice() == null &&
            product.getDescription() == null || 
            product.getDescription().trim().isEmpty()
        )? messageFieldsEmpty : null;
    }

    @Transactional
    @Override
    public ResponseEntity<?> getProduct(Client client) {
        List<Product> listProducts =  (List<Product>) productRepository.findAll();
        
        
        System.out.println(listProducts.get(0).getClient());
        System.out.println(client);

        listProducts = listProducts.stream()
        .filter(product -> product.getClient().getId() == client.getId())
        .collect(Collectors.toList());
        
        return (listProducts.size() > 0)?
        new ResponseEntity<>(constructorGetProducts(listProducts), HttpStatus.OK):
        new ResponseEntity<>("No exists products", HttpStatus.NOT_FOUND);
        
    }

    @Transactional
    @Override
    public ResponseEntity<?> getAllProducts(Long idCategory, Double price,String name) {
        List<Product> listProducts =  (List<Product>) productRepository.findAll();
        listProducts = listProducts.stream()
        .sorted(Comparator.comparingDouble(Product::getRating).reversed())
        .collect(Collectors.toList());

        if(idCategory != null){
            List<Product> productsByCategory =  new ArrayList<>();
            Optional<Category> categories = categoryRepository.findById(idCategory);
            System.out.println(categories.get());
            if(categories.isPresent()){
                System.out.println("ENTRANDO A CONDICIONAL");
                productsByCategory = categories.get().getProducts();
                listProducts = listProducts.stream().filter(productsByCategory::contains)
                .collect(Collectors.toList());
            }
        }

        if(name != null){
           ArrayList<Product> products = productRepository.findByNameContainingIgnoreCase(name);
           listProducts = listProducts.stream().filter(products::contains)
            .collect(Collectors.toList());
        }

        if(price != null){
            
            listProducts = listProducts.stream()
            .filter(product -> 
            (product.getPrice() - ((product.getDiscount()/100)*product.getPrice())) <= price)
            .collect(Collectors.toList());
        }

        return (listProducts.size() > 0)?
        new ResponseEntity<>(constructorGetProducts(listProducts), HttpStatus.OK):
        new ResponseEntity<>("No exists products", HttpStatus.NOT_FOUND);
    }

    private ArrayList<ModelListProducts> constructorGetProducts(List<Product> listProducts) {
        BuilderGetProductsImpl builder = new BuilderGetProductsImpl();
        
        ArrayList<ModelListProducts> requestProducts = 
        new ArrayList<ModelListProducts>();

        for (Product product : listProducts) {
            requestProducts.add(
                builder.setId(product.getId())
                       .setTitle(product.getName())
                       .setDiscount(product.getDiscount())
                       .setPrice(product.getPrice(), product.getDiscount())
                       .setImage(product.getImageProfile())
                       .setDetailProduct(product.getId().toString())
                       .setRating(product.getRating())
                       .ModelListProducts()
                       
            );
        }
        return requestProducts;
    }

    @Transactional
    @Override
    public ResponseEntity<?> getDetailProductById(Long id, Client client) {
        final ResponseEntity<?> messageProductNotExists = 
        new ResponseEntity<>("The product not exists", 
        HttpStatus.NOT_FOUND);
        /*
        final ResponseEntity<?> messageProductNotFound = 
        new ResponseEntity<>("The product not found", 
        HttpStatus.FORBIDDEN);
        */
        Optional<Product> product = productRepository.findById(id);
        
        if(product.isPresent()){

            if(client == null) 
            return new ResponseEntity<>(constructorGetProductById(product.get()),HttpStatus.OK); 
            
            return new ResponseEntity<>(
                                         constructorGetProductById(product.get(),client), 
                                         HttpStatus.OK);
        }else{
            return messageProductNotExists;
        }
    }

    private ModelDetailProduct constructorGetProductById(Product product, Client client) {
        //TODO PONER VARIABLES PARA AÑADIR PRODUCTO O ACTUALIZAR CARRITO
        //TODO PONER VARIABLES PARA ACTUALIZAR EL PRODUCTO O ELIMINARLO SI ES EL DUEÑO
        BuilderGetProductByIdImpl builder = new BuilderGetProductByIdImpl();
        ModelDetailProduct requestProduct = new ModelDetailProduct();
        Long idCart;

        List<Cart> listOfCarts = client.getCart().stream()
        .filter(c -> c.getEnumState() == EnumState.ACTIVE).collect(Collectors.toList());

        if(listOfCarts.size() > 0){
            idCart = listOfCarts.get(0).getId();
        }else{
            idCart = null;
        }

        requestProduct = builder.setId(product.getId())
                        .setName(product.getName())
                        .setPrice(product.getPrice(), product.getDiscount())
                        .setDescription(product.getDescription())
                        .setStock(product.getStock())
                        .setContent(product.getContent())
                        .setRating(product.getRating())
                        .setDiscount(product.getDiscount())
                        .setRegistration(product.getRegistration())
                        .setCategories(product.getCategories())
                        .setClient((Client) product.getClient())
                        .setImage(product.getImageProfile())
                        .setPostImages(product.getImagePost())
                        .setQuantitySold(product.getListReports())
                        .setOptDeleteProduct(product,client.getId())
                        .setOptCreateUpdateCartWithProduct(
                        product.getClient().getId(),client.getId(),idCart)
                        .modelDetailProduct();
        return requestProduct;
    }

    private ModelDetailProduct constructorGetProductById(Product product) {
        BuilderGetProductByIdNoLogued builder = new BuilderGetProductByIdNoLogued();
        ModelDetailProduct requestProduct = new ModelDetailProduct();


        requestProduct = builder.setId(product.getId())
                        .setName(product.getName())
                        .setPrice(product.getPrice(), product.getDiscount())
                        .setDescription(product.getDescription())
                        .setStock(product.getStock())
                        .setContent(product.getContent())
                        .setRating(product.getRating())
                        .setDiscount(product.getDiscount())
                        .setRegistration(product.getRegistration())
                        .setCategories(product.getCategories())
                        .setClient((Client) product.getClient())
                        .setImage(product.getImageProfile())
                        .setPostImages(product.getImagePost())
                        .setQuantitySold(product.getListReports())
                        .setOptDeleteProduct()
                        .setOptCreateUpdateCartWithProduct()
                        .modelDetailProduct();
        return requestProduct;
    }
    

    @Transactional
    @Override
    public ResponseEntity<?> deleteProduct(Long id) {
        ResponseEntity<?> request = getProductById(id);
        
        Product product = (Product) request.getBody();
        deleteProductByCart(product);
        productRepository.delete(product);
        return new ResponseEntity<>("Product deleted",HttpStatus.OK);
    }

    private void deleteProductByCart(Product product){
        List<Cart> listCarts = (List<Cart>) cartRepository.findAll();
        for (Cart cart : listCarts) {
            List <LineProduct> listProducts = cart.getLineProducts();
            listProducts = listProducts.stream().filter(line -> line.getProduct().getId() == product.getId()).collect(Collectors.toList());
            if(listProducts.size() > 0){
                
                cart.getLineProducts().removeAll(listProducts);
                cartRepository.save(cart);
            }
        }
    }

    @Transactional
    @Override
    public ResponseEntity<?> getProductById(Long id) {
        final ResponseEntity<?> messageProductNotExists = 
        new ResponseEntity<>("The product not exists", 
        HttpStatus.NOT_FOUND);
        Optional<Product> product = productRepository.findById(id);
        
        if(product.isPresent()){
            
            return new ResponseEntity<>(product.get(),HttpStatus.OK);
        }else{
            return messageProductNotExists;
        }
    }
    
    @Transactional
    @Override
    public ResponseEntity<?> updateProduct(FormProduct productUpdated, 
                                            Long id,
                                            ArrayList<ImagePost> postImage, 
                                            ImageProfile image) {
        final ResponseEntity<?> messageProductNotExists = 
        new ResponseEntity<>("The product not exists", 
        HttpStatus.NOT_FOUND); 

        if(getProductById(id).getStatusCodeValue() != 200) 
        return messageProductNotExists;    

        Product productRequest = (Product) getProductById(id).getBody();                                        
        final ResponseEntity<?> messageCategoryNotExists =
        new ResponseEntity<>("One category or more is not exists", 
        HttpStatus.NOT_FOUND);
                                           
        controlUpdateFields(productUpdated,productRequest);
        
        if(productRequest.getCategories() == null)
        productRequest.setCategories(new ArrayList<Category>());

        if(productUpdated != null && 
        controlCategoriesUpdated(productUpdated,productRequest))
        return messageCategoryNotExists;                                        
        
        controlImagesProfile(productUpdated, productRequest, postImage, image);
        if(postImage != null){
            //TODO QUEDA PENDIENTE MEJORAR EL UPDATE DE IMAGENES
            productRequest.setImagePost(postImage);
        }                                        
                                                
        productRepository.save(productRequest);                                        
        return new ResponseEntity<>("Updated Sucessfully",HttpStatus.OK);
    }

    private void controlImagesProfile(
        FormProduct productUpdated,
        Product productRequest,ArrayList<ImagePost> postImage, 
        ImageProfile image)
        {

            if(image != productRequest.getImageProfile()){
                if(productRequest.getImageProfile() != null)
                imageRepository.delete(productRequest.getImageProfile());
                productRequest.setImageProfile(image);
            }
    }

    private void controlUpdateFields(FormProduct productUpdated, 
                                        Product productRequest){

        if(productUpdated.getName() != null) 
        productRequest.setName(productUpdated.getName());

        if(productUpdated.getPrice() != null)
        productRequest.setPrice(productUpdated.getPrice());

        if(productUpdated.getStock() != productRequest.getStock())
        productRequest.setStock(productUpdated.getStock());
               
        if(productRequest.getDiscount() != productUpdated.getDiscount()){
            productRequest.setDiscount(productUpdated.getDiscount());
            
            productRequest.setPrice(
                productRequest.getPrice() -
                ((productUpdated.getDiscount()/100)*productUpdated.getPrice()));
        }
        

        if(productUpdated.getDescription() != null)
        productRequest.setDescription(productUpdated.getDescription());

        if(productUpdated.getContent() != null)
        productRequest.setContent(productUpdated.getContent());
    }

    private boolean controlCategoriesUpdated(FormProduct productUpdated, Product productRequest){
        List<Category> categoriesUpdated = new ArrayList<Category>(); 
        if(productUpdated.getCategories().size() > 0){                       
            Optional<Category> categoryRequest;

            for (Category category : productUpdated.getCategories()) {
                categoryRequest = categoryRepository.findById(category.getId());
                if(categoryRequest.isPresent()){
                    if(!productRequest.getCategories().contains(category)){
                        categoriesUpdated.add(categoryRequest.get());
                        categoryRequest.get().getProducts().add(productRequest);
                    }
                }else{
                    categoriesUpdated.add(null);
                }
            }
            //Hay una categoria que no existe. Emitimos error!
            if(categoriesUpdated.contains(null)) return true;
        }else{
            for (Category category : productRequest.getCategories()) {
                category.getProducts().remove(productRequest);
            }
        }
        productRequest.setCategories(categoriesUpdated);
        return false;
    }

    @Override
    public ResponseEntity<?> getProductById(Long id, User user) {
        Optional<Product> product = productRepository.findById(id);
        
       
        if(!product.isPresent())
        return new ResponseEntity<>("No exists products", HttpStatus.NOT_FOUND);
        
        if(product.get().getId() != id)
        return new ResponseEntity<>("No exists products", HttpStatus.FORBIDDEN);
        
        
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    public ResponseEntity<?> getProductByCategory(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        
        if(category.isPresent()){
            List<Product> products = category.get().getProducts();
            
            ArrayList<ModelListProducts> productsResponse = 
            constructorGetProducts((ArrayList<Product>) products);
            return new ResponseEntity<>(productsResponse,HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Category not found",HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<?> getProductsPopularsByCategory(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
       
       if(category.isPresent()){
            List<Product> products = category.get().getProducts();
            products = products.stream()
            .filter(p -> p.getRating() >= 4.00)
            .collect(Collectors.toList());

            ArrayList<ModelListProducts> productsResponse = 
            constructorGetProducts((ArrayList<Product>) products);

            return new ResponseEntity<>(productsResponse,HttpStatus.OK);
       }else{
            return new ResponseEntity<>("Category not found",HttpStatus.NOT_FOUND);
       }
    }

    @Override
    public ResponseEntity<?> getProduct() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResponseEntity<?> getDetailProductById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
