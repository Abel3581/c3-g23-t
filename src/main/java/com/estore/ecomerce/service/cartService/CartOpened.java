package com.estore.ecomerce.service.cartService;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import com.estore.ecomerce.domain.Cart;
import com.estore.ecomerce.domain.Client;
import com.estore.ecomerce.domain.Invoice;
import com.estore.ecomerce.domain.LineProduct;
import com.estore.ecomerce.domain.Product;
import com.estore.ecomerce.dto.ModelDetailCart;
import com.estore.ecomerce.dto.forms.FormCartProduct;
import com.estore.ecomerce.repository.CartRepository;
import com.estore.ecomerce.repository.LineRepository;
import com.estore.ecomerce.repository.ProductRepository;
import com.estore.ecomerce.utils.build.BuilderGetCartByIdImpl;
import com.estore.ecomerce.utils.enums.EnumState;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
@Service
public class CartOpened implements ICartState{

    @Autowired
    private LineRepository lineRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public ResponseEntity<?> closeCart(Cart cart) {
        //TODO añadir el reporte de cada cliente y su producto
        cart.setEnumState(EnumState.CLOSED);
        discountStockOfProducts(cart);
        updateRatingProducts(cart);
        confirmLineOfCart(cart);
        generateInvoice(cart);
        return new ResponseEntity<>(generateInvoice(cart), 
        HttpStatus.OK);
    }

    @Transactional
    @Override
    public ResponseEntity<?> updateCart(Cart cart, List<FormCartProduct> lineProduct) {
        System.out.println("Entrando a actualizar!");
        cart.setEnumState(EnumState.ACTIVE);
        updateAddProductsByCart(cart,lineProduct);
        updateDeleteProductsByCart(cart,lineProduct);
        updateAmounOfLinesCarts(cart, lineProduct);  
        return new ResponseEntity<>(cart, HttpStatus.OK);
    } 

    private void updateAddProductsByCart(Cart cart, List<FormCartProduct> lineProduct){
        List<Product> listProducts = new ArrayList<Product>();
        List<LineProduct> listOfnewProducts = new ArrayList<LineProduct>();
        for (LineProduct line : cart.getLineProducts()){
            Product product = productRepository.
            findById(line.getProduct().getId())
            .get();
            listProducts.add(product);
        }
        for (FormCartProduct line : lineProduct) {
            Product product = productRepository.findById(line.getId()).get();
            listOfnewProducts.add(
                new LineProduct(line.getAmount(),product,cart)
            );
        }
        for (LineProduct product : listOfnewProducts) {
            if(!listProducts.contains(product.getProduct())){
                cart.getLineProducts().add(product);     
            }
        }
    }

    private void updateDeleteProductsByCart(Cart cart, List<FormCartProduct> lineProduct){
        List<Product> listProducts = new ArrayList<Product>();
        List<LineProduct> listOfLineProducts = new ArrayList<LineProduct>();
        
        for (FormCartProduct line : lineProduct) {
            Product product = productRepository.findById(line.getId()).get();
            listProducts.add(product);
        }
        for (LineProduct line : cart.getLineProducts()) {
            if(!listProducts.contains(line.getProduct())){
                listOfLineProducts.add(line);
            }   
        }
        if(listOfLineProducts.size() > 0){
            cart.getLineProducts().removeAll(listOfLineProducts);
            //lineRepository.deleteAll(listOfLineProducts);
        }
        
    }

    
    private void updateAmounOfLinesCarts(Cart cart, List<FormCartProduct> lineProduct){
        if(lineProduct.size() > 0){
            for (FormCartProduct line : lineProduct) {
                for (LineProduct lineCart : cart.getLineProducts()) {
                    if(lineCart.getProduct().getId() == line.getId()){
                        lineCart.setAmount(line.getAmount());
                        lineRepository.save(lineCart);
                    }    
                }
            }
        }
    }

    @Override
    public void openCart(Long id) {}

    private void createLineOfProduct(Cart cart){
        List<LineProduct> listLineProducts = cart.getLineProducts();
        cart.setLineProducts(new ArrayList<LineProduct>());
        
        for (LineProduct line : listLineProducts){
            cart.getLineProducts().add(
                new LineProduct(line.getAmount(), line.getProduct(), cart)
            );
        }
    }  

    private void updateRatingProducts(Cart cart){
        for (LineProduct line : cart.getLineProducts()) {
            if(line.getProduct().getRating() < 5.0){
                line.getProduct().setRating((line.getProduct().getRating() + 0.05));
                productRepository.save(line.getProduct());
            }
            
        }
    }

    private Invoice generateInvoice(Cart cart){
        Invoice invoice = new Invoice();
        invoice.setCart(cart);
        invoice.setObservation("Default Observation");
        return invoice;
    }

    private void confirmLineOfCart(Cart cart){
        List<LineProduct> listLineProducts = cart.getLineProducts();
        List<LineProduct> newListLineProducts = new ArrayList<LineProduct>();

        for (LineProduct line : listLineProducts) {
            newListLineProducts.add(
               new LineProduct(line.getAmount(), new Product(
                   line.getProduct().getName(),
                   line.getProduct().getPrice(),
                   line.getProduct().getDescription(),
                   line.getProduct().getStock(),
                   null,
                   line.getProduct().getRating(),
                   line.getProduct().getDiscount(),
                   line.getProduct().getRegistration(),
                   line.getProduct().getCategories(),
                   line.getProduct().getClient(),
                   null,
                   null,
                   null
               ), cart)
           );             
        }
        lineRepository.deleteAll(listLineProducts);
    }

    private void discountStockOfProducts(Cart cart){
        int stock = 0;
        for (LineProduct line : cart.getLineProducts()){
            stock = line.getProduct().getStock();
            stock = stock - line.getProduct().getStock();
            line.getProduct().setStock(stock);
        }
    }

    @Override
    public ModelDetailCart getCart(Cart cart) {
        BuilderGetCartByIdImpl builder = new BuilderGetCartByIdImpl();
        ModelDetailCart cartRequest = new ModelDetailCart();
        cartRequest = builder.setId(cart.getId())
        .setClient(cart.getBuyer())
        .setEnumState(cart.getEnumState())
        .setLineProduct(cart, cart.getLineProducts())
        .setRegistration(cart.getRegistration())
        .setOptCleanCart(cart.getId(), cart.getEnumState())
        .setOptCloseCart(cart.getId(), cart.getEnumState())
        .setInvoice(cart.getId(), cart.getEnumState())
        .setTotal(cart.getLineProducts())
        .builderGetCartById();

        return cartRequest;
    }

    @Override
    public ResponseEntity<?> deleteProducts(Cart cart, LineProduct line) {
        cart.getLineProducts().remove(line);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }


}
