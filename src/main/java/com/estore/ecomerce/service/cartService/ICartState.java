package com.estore.ecomerce.service.cartService;

import com.estore.ecomerce.domain.Cart;
import com.estore.ecomerce.dto.ModelDetailCart;

public interface ICartState {

    public void closeCart(Cart cart);
    public void updateCart(Cart cart);
    public void openCart(Long id);
    public ModelDetailCart getCart(Cart cart);
}
