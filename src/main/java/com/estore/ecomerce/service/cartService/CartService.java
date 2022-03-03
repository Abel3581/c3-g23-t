package com.estore.ecomerce.service.cartService;

import java.util.List;

import com.estore.ecomerce.domain.Cart;
import com.estore.ecomerce.domain.Client;
import com.estore.ecomerce.dto.forms.FormCartProduct;

import org.springframework.http.ResponseEntity;

public interface CartService {
    public ResponseEntity<?> createCart(Client client, List<FormCartProduct> lineProduct);
    public ResponseEntity<?> updateCart(Cart cart, List<FormCartProduct> lineProduct);
    public ResponseEntity<?> closeCart(Client client, Long id);
    public ResponseEntity<?> deleteCartById(Long id);

    public ResponseEntity<?> getCart();
    public ResponseEntity<?> getCartById(Long id);

}
