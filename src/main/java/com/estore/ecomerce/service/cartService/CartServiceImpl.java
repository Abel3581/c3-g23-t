package com.estore.ecomerce.service.cartService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import java.util.function.Predicate;


import com.estore.ecomerce.domain.Cart;
import com.estore.ecomerce.domain.Client;
import com.estore.ecomerce.domain.LineProduct;
import com.estore.ecomerce.domain.Product;
import com.estore.ecomerce.dto.forms.FormCartProduct;
import com.estore.ecomerce.repository.CartRepository;
import com.estore.ecomerce.repository.ProductRepository;
import com.estore.ecomerce.utils.enums.EnumState;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService{

   
    private ProductRepository productRepository;
    private CartRepository cartRepository;
    private ICartState cartOpened;
    private ICartState actualState;

    @Autowired
    public CartServiceImpl(ProductRepository productRepository, CartRepository cartRepository, ICartState cartOpened){
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.cartOpened = cartOpened;
    }


    private void asignStateCart(Cart cart){
        switch (cart.getEnumState()) {
            case CLOSED:
                //actualState = cartClosed;
                break;
            case ACTIVE:
                actualState = cartOpened;
                break;
            case SUSPENDED:
                //actualState = cartSuspended;
                break;
            case CANCELED:  
                //actualState = cartCanceled;
                break; 
            default:
                break;
        }
    }

    @Transactional
    @Override
    public ResponseEntity<?> createCart(Client client, List<FormCartProduct> lineProduct) {
        final ResponseEntity<?> messagelistProductEmpty =
        new ResponseEntity<>("The cart must have at least one Product", 
        HttpStatus.NOT_ACCEPTABLE);
        final ResponseEntity<?> messageProductNotExists =
        new ResponseEntity<>("One product or more is not exists", 
        HttpStatus.NOT_FOUND);
        final ResponseEntity<?> messageStockOfProductInvalid =
        new ResponseEntity<>("Stock insuficient", 
        HttpStatus.NOT_ACCEPTABLE);
        final ResponseEntity<?> messageAmountOfProductInvalid =
        new ResponseEntity<>("The amount of products must be major to zero", 
        HttpStatus.NOT_ACCEPTABLE);
        final ResponseEntity<?> messageMusntUniqueInList =
        new ResponseEntity<>("The products must be unique in cart", 
        HttpStatus.NOT_ACCEPTABLE);
        final ResponseEntity<?> messageCartOpenIsntUnique =
        new ResponseEntity<>("Already exist a cart active", 
        HttpStatus.NOT_ACCEPTABLE);

        Cart cart = new Cart();
        cart.setEnumState(EnumState.ACTIVE);
        cart.setBuyer(client);
        
        if(controlUniqueCartOpen(client)) return messageCartOpenIsntUnique;
        if(lineProduct.size()== 0)return messagelistProductEmpty;
        //Controlar productos son validos
        if(controlProducts(lineProduct).size() != lineProduct.size())
        return messageProductNotExists;
        if(controlUniqueProductByList(lineProduct).size() != lineProduct.size())
        return messageMusntUniqueInList;
        if(controlAmountOfProducts(lineProduct).size() != lineProduct.size())
        return messageAmountOfProductInvalid;
        if(controlStockOfProducts(lineProduct).size() != lineProduct.size())
        return messageStockOfProductInvalid;
        
        createLinesCarts(cart, lineProduct);
        cartRepository.save(cart);
        return new ResponseEntity<>("Cart created succesfully!", HttpStatus.OK);
    }

    private void createLinesCarts(Cart cart, List<FormCartProduct> lineProduct){
        Product product; 
        for (FormCartProduct productLine : lineProduct) {
            product = productRepository.findById(productLine.getId()).get();

            cart.getLineProducts().add(
                new LineProduct(productLine.getAmount(), product, cart)
            );
        }
    }

    private void updateStockByEachProduct(Cart cart){
        cart.getLineProducts().stream().forEach(line ->
        productRepository.save(line.getProduct()));
    }

    private boolean controlUniqueCartOpen(Client client){
        List<Cart> carts = new ArrayList<Cart>();
        carts = client.getCart();
        if(carts.size() != 0){
            carts.stream().filter(
                cart -> cart.getEnumState() == EnumState.ACTIVE
            );
        }else{
            return false;
        }
        return (carts.size() > 0)?true:false;
    }

    private List<FormCartProduct> controlProducts(List<FormCartProduct> lineProduct){
        //Control de existencia del producto

        Predicate<FormCartProduct> condition = new Predicate<FormCartProduct>(){
            @Override
            public boolean test(FormCartProduct line){
                if(productRepository.findById(line.getId()).isPresent()){
                    //Si producto existe
                    return true;
                }
                    //Si producto no existe
                    return false;
            }
        };
        lineProduct = lineProduct.stream()
        .filter(condition)
        .collect(Collectors.toList());
        
        return lineProduct;
        //Control del stock del producto.
    }

    private List<FormCartProduct> controlStockOfProducts(List<FormCartProduct> lineProduct){
        //Control de existencia del producto

        Predicate<FormCartProduct> condition = new Predicate<FormCartProduct>(){
            @Override
            public boolean test(FormCartProduct line){
                if(productRepository.
                        findById(line.getId())
                        .get().getStock() >= line.getAmount()){
                        //Si producto existe
                        return true;
                    }
                
                    //Si producto no existe
                    return false;
                }
            };
        lineProduct = lineProduct.stream()
        .filter(condition)
        .collect(Collectors.toList());
        
        return lineProduct;
        //Control del stock del producto.
    }

    private List<FormCartProduct> controlAmountOfProducts(List<FormCartProduct> lineProduct){
        Predicate<FormCartProduct> condition = new Predicate<FormCartProduct>(){
            @Override
            public boolean test(FormCartProduct line){
                if(line.getAmount() > 0){
                        //Si producto existe
                        return true;
                    }
                    //Si producto no existe
                    return false;
                }
            };
        lineProduct = lineProduct.stream()
        .filter(condition)
        .collect(Collectors.toList());
        
        return lineProduct;
    }

    private List<Long> controlUniqueProductByList(List<FormCartProduct> lineProduct){
        List<FormCartProduct> listLineProducts = lineProduct;
        List<Long> listIdProducts = listLineProducts.stream()
        .map(l->l.getId())
        .collect(Collectors.toList());
        
        listIdProducts = listIdProducts.stream().sorted().collect(Collectors.toList()); 
        listIdProducts = listIdProducts.stream().distinct().collect(Collectors.toList());
        return listIdProducts;
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
    
    @Transactional
    @Override
    public ResponseEntity<?> getCartById(Long id) {
        Cart cart = cartRepository.findById(id).get();
        asignStateCart(cart);
        
        return new ResponseEntity<>(actualState.getCart(cart), HttpStatus.OK);
    }


    @Override
    public ResponseEntity<?> deleteCartById(Long id) {
        cartRepository.deleteById(id);
        return new ResponseEntity<>("Deleted cart sucesfully!", HttpStatus.OK);
    }


}
