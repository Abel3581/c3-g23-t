package com.estore.ecomerce.service.cartService;

import java.util.List;

import com.estore.ecomerce.domain.Cart;
import com.estore.ecomerce.domain.Client;
import com.estore.ecomerce.dto.forms.FormCartProduct;
import com.estore.ecomerce.utils.enums.EnumState;

import org.springframework.http.ResponseEntity;

public interface CartService {
    public ResponseEntity<?> createCart(Client client, List<FormCartProduct> lineProduct);
    public ResponseEntity<?> addProducts(Client client, Long idCart, List<FormCartProduct> lineProduct);
    public ResponseEntity<?> deleteProducts(Client client, Long idCart, Long idLine);
    public ResponseEntity<?> closeCart(Client client, Long id);
    public ResponseEntity<?> deleteCartById(Long id, Client client);

    public ResponseEntity<?> getCart(Client cliente, EnumState state);
    public ResponseEntity<?> getCartById(Long id, Client cliente);
    public ResponseEntity<?> getCartByOpened(Client cliente);

}
