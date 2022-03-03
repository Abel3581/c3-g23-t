package com.estore.ecomerce.service.cartService;

import com.estore.ecomerce.domain.Cart;
import com.estore.ecomerce.domain.Invoice;
import com.estore.ecomerce.dto.ModelDetailCart;

import org.springframework.http.ResponseEntity;

public interface ICartState {

    public ResponseEntity<?> closeCart(Cart cart);
    public void updateCart(Cart cart);
    public void openCart(Long id);
    public ModelDetailCart getCart(Cart cart);
}
