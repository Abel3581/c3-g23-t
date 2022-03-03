package com.estore.ecomerce.service.cartService;

import com.estore.ecomerce.domain.Cart;
import com.estore.ecomerce.domain.Invoice;
import com.estore.ecomerce.dto.ModelDetailCart;

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
    public void updateCart(Cart cart) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void openCart(Long id) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public ModelDetailCart getCart(Cart cart) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
