package com.estore.ecomerce.service.cartService;

import java.util.ArrayList;
import java.util.List;

import com.estore.ecomerce.domain.Cart;
import com.estore.ecomerce.domain.LineProduct;
import com.estore.ecomerce.dto.ModelDetailCart;
import com.estore.ecomerce.utils.build.BuilderGetCartByIdImpl;
import com.estore.ecomerce.utils.enums.EnumState;

import org.springframework.stereotype.Service;
@Service
public class CartOpened implements ICartState{


    @Override
    public void closeCart(Cart cart) {
        cart.setEnumState(EnumState.CLOSED);
        discountStockOfProducts(cart);
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


    private void discountStockOfProducts(Cart cart){
        for (LineProduct line : cart.getLineProducts()){
            int stock = line.getProduct().getStock();
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
