package com.estore.ecomerce.service.cartService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.function.Predicate;


import com.estore.ecomerce.domain.Cart;
import com.estore.ecomerce.domain.Client;
import com.estore.ecomerce.domain.Product;
import com.estore.ecomerce.repository.CartRepository;
import com.estore.ecomerce.repository.IUserRepository;
import com.estore.ecomerce.repository.ProductRepository;
import com.estore.ecomerce.utils.enums.EnumState;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService{

    @Autowired
    ProductRepository productRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    IUserRepository userRepository;


    ICartState cartCanceled;
    ICartState cartClosed;
    ICartState cartOpened;
    ICartState cartSuspended;
    ICartState actualState;

    @Override
    public ResponseEntity<?> createCart(Client client, List<Product> listProducts) {
        final ResponseEntity<?> messagelistProductEmpty =
        new ResponseEntity<>("The cart must have at least one Product", 
        HttpStatus.NOT_ACCEPTABLE);
        final ResponseEntity<?> cartActiveEmpty =
        new ResponseEntity<>("You have one cart active", 
        HttpStatus.NOT_ACCEPTABLE);
        final ResponseEntity<?> messageProductNotExists =
        new ResponseEntity<>("One product or more is not exists", 
        HttpStatus.NOT_FOUND);

        // Estado del carrito pasa a ser opened.

        //Control de minimo un producto en la lista
        if(listProducts.size() == 0)return messagelistProductEmpty;
        
        //Control de unico carrito activo
        List<Cart> carritos = client.getCart();
        if(carritos.size()>0){
            carritos = carritos.stream()
            .filter(c -> c.getEnumState() != EnumState.ACTIVE)
            .collect(Collectors.toList());
        }
        if(carritos.size() != client.getCart().size()) return cartActiveEmpty;

        //Controlar productos son validos
        if(controlProducts(listProducts).size() != listProducts.size())
        return messageProductNotExists;

        

        return null;
    }

    private List<Product> controlProducts(List<Product> listProducts){
        Predicate<Product> condition = new Predicate<Product>(){
            @Override
            public boolean test(Product product){
                if(productRepository.findById(product.getId()).isPresent()){
                    //Si producto existe
                    return true;
                }
                    //Si producto no existe
                    return false;
            }
        };
        listProducts = listProducts.stream()
        .filter(condition)
        .collect(Collectors.toList());
        
        return listProducts;
    }
    @Override
    public ResponseEntity<?> closeCart(Long id) {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public ResponseEntity<?> updateCart(Long id) {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public ResponseEntity<?> getCart() {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public ResponseEntity<?> getCartById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }


}
