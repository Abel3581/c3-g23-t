package com.estore.ecomerce.service.cartService;

import java.util.List;

import com.estore.ecomerce.domain.Client;
import com.estore.ecomerce.dto.forms.FormCartProduct;

import org.springframework.http.ResponseEntity;

public interface CartService {
    public ResponseEntity<?> createCart(Client client, List<FormCartProduct> lineProduct);
    public ResponseEntity<?> closeCart(Long id);
    public ResponseEntity<?> updateCart(Long id);
    public ResponseEntity<?> deleteCartById(Long id);

    public ResponseEntity<?> getCart();
    public ResponseEntity<?> getCartById(Long id);

}
