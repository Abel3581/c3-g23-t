package com.estore.ecomerce.service.cartService;

import java.util.List;

import com.estore.ecomerce.domain.Cart;
import com.estore.ecomerce.domain.Invoice;
import com.estore.ecomerce.domain.LineProduct;
import com.estore.ecomerce.dto.ModelDetailCart;
import com.estore.ecomerce.dto.forms.FormCartProduct;

import org.springframework.http.ResponseEntity;

public interface ICartState {

    public ResponseEntity<?> closeCart(Cart cart);
    public ResponseEntity<?> updateCart(Cart cart, List<FormCartProduct> lineProduct);
    public ResponseEntity<?> deleteProducts(Cart cart, LineProduct line);
    public void openCart(Long id);
    public ModelDetailCart getCart(Cart cart);
}
