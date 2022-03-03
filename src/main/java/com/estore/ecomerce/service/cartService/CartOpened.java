package com.estore.ecomerce.service.cartService;

import java.util.ArrayList;
import java.util.List;

import com.estore.ecomerce.domain.Cart;
import com.estore.ecomerce.domain.Invoice;
import com.estore.ecomerce.domain.LineProduct;
import com.estore.ecomerce.domain.Product;
import com.estore.ecomerce.dto.ModelDetailCart;
import com.estore.ecomerce.repository.LineRepository;
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

    @Override
    public ResponseEntity<?> closeCart(Cart cart) {
        //TODO añadir el reporte de cada cliente y su producto
        cart.setEnumState(EnumState.CLOSED);
        discountStockOfProducts(cart);
        confirmLineOfCart(cart);
        generateInvoice(cart);
        return new ResponseEntity<>(generateInvoice(cart), 
        HttpStatus.OK);
    }

    @Override
    public void updateCart(Cart cart) {
        cart.setEnumState(EnumState.ACTIVE);
        createLineOfProduct(cart);
        System.out.println("\n Lineas del carrito : "+ cart.getLineProducts());
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
        .setLineProduct(cart.getLineProducts())
        .setRegistration(cart.getRegistration())
        .setTotal(cart.getLineProducts())
        .builderGetCartById();

        return cartRequest;
    }


}
