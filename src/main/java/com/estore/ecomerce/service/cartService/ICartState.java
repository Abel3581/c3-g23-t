package com.estore.ecomerce.service.cartService;


public interface ICartState {
    public void openCart();
    public void updateCart(Long id);
    public void closeCart(Long id);
    public void cancelCart(Long id);
    
}
