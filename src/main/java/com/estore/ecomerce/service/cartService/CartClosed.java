package com.estore.ecomerce.service.cartService;

import java.util.List;

import com.estore.ecomerce.domain.Cart;
import com.estore.ecomerce.domain.LineProduct;
import com.estore.ecomerce.dto.ModelDetailCart;
import com.estore.ecomerce.dto.forms.FormCartProduct;
import com.estore.ecomerce.utils.build.BuilderGetCartByIdImpl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CartClosed implements ICartState{

    @Override
    public ResponseEntity<?> closeCart(Cart cart) {
        final ResponseEntity<?> messageCarthaveinvoice =
        new ResponseEntity<>("Already the cart have an invoice", 
        HttpStatus.FORBIDDEN);
        
        return messageCarthaveinvoice;
    }

    @Override
    public ResponseEntity<?> addProducts(Cart cart, List<FormCartProduct> lineProduct) {
        return new ResponseEntity<>("Error the cart is closed", 
        HttpStatus.FORBIDDEN);
    }

    @Override
    public ResponseEntity<?> deleteProducts(Cart cart, LineProduct line) {
        return new ResponseEntity<>("Error the cart is closed", 
        HttpStatus.FORBIDDEN);
    }

    @Override
    public void openCart(Long id) {}

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
    
    
}
