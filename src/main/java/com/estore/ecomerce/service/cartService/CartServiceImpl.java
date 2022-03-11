package com.estore.ecomerce.service.cartService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import java.util.function.Predicate;


import com.estore.ecomerce.domain.Cart;
import com.estore.ecomerce.domain.Client;
import com.estore.ecomerce.domain.Invoice;
import com.estore.ecomerce.domain.LineProduct;
import com.estore.ecomerce.domain.Product;
import com.estore.ecomerce.dto.ModelDetailCart;
import com.estore.ecomerce.dto.ModelDetailInvoice;
import com.estore.ecomerce.dto.ModelListCart;
import com.estore.ecomerce.dto.forms.FormCartProduct;
import com.estore.ecomerce.repository.CartRepository;
import com.estore.ecomerce.repository.InvoiceRepository;
import com.estore.ecomerce.repository.LineRepository;
import com.estore.ecomerce.repository.ProductRepository;
import com.estore.ecomerce.service.InvoiceService;
import com.estore.ecomerce.utils.build.BuilderGetCartsImpl;
import com.estore.ecomerce.utils.enums.EnumState;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService{

   
    private ProductRepository productRepository;
    private CartRepository cartRepository;
    private InvoiceRepository invoiceRepository;
    private LineRepository lineRepository;
    private InvoiceService invoiceService;
    private CartOpened cartOpened;
    private ICartState actualState;
    private CartClosed cartClosed;


    @Autowired
    public CartServiceImpl(ProductRepository productRepository, CartRepository cartRepository, CartOpened cartOpened
    ,CartClosed cartClosed,InvoiceRepository invoiceRepository, LineRepository lineRepository, InvoiceService invoiceService){
        this.productRepository = productRepository;
        this.invoiceRepository = invoiceRepository;
        this.cartRepository = cartRepository;
        this.lineRepository = lineRepository;
        this.cartOpened = cartOpened;
        this.cartClosed = cartClosed;
        this.invoiceService = invoiceService;
    }


    private void asignStateCart(Cart cart){
        switch (cart.getEnumState()) {
            case CLOSED:
                actualState = cartClosed;
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
    public ResponseEntity<?> updateCart(Client client, Long idCart, List<FormCartProduct> lineProduct){
        final ResponseEntity<?> messageStockOfProductInvalid =
        new ResponseEntity<>("Stock insuficient", 
        HttpStatus.NOT_ACCEPTABLE);
        final ResponseEntity<?> messageAmountOfProductInvalid =
        new ResponseEntity<>("The amount of products must be major to zero", 
        HttpStatus.NOT_ACCEPTABLE);
        final ResponseEntity<?> messageProductNotExists =
        new ResponseEntity<>("One product or more is not exists", 
        HttpStatus.NOT_FOUND);
        final ResponseEntity<?> messageCartisForbidden =
        new ResponseEntity<>("Cart is not found", 
        HttpStatus.FORBIDDEN);
        final ResponseEntity<?> messageCartIsnotexists =
        new ResponseEntity<>("Cart is not found", 
        HttpStatus.NOT_FOUND);

        Optional<Cart> cart = cartRepository.findById(idCart);
        if(!cart.isPresent()) return messageCartIsnotexists;
        if(cart.get().getBuyer().getId() != client.getId()) return messageCartisForbidden;
        
        asignStateCart(cart.get());

        if(lineProduct == null)
        return new ResponseEntity<>("List of products is not acceptable", 
        HttpStatus.NOT_ACCEPTABLE);

        if(lineProduct.size() == 0)
        return new ResponseEntity<>("List of products is not acceptable", 
        HttpStatus.NOT_ACCEPTABLE);
        
        if(controlProducts(lineProduct).size() != lineProduct.size())
        return messageProductNotExists;

        if(controlStockOfProducts(lineProduct).size() != lineProduct.size())
        return messageStockOfProductInvalid;

        if(controlAmountOfProducts(lineProduct).size() != lineProduct.size())
        return messageAmountOfProductInvalid;

        ResponseEntity<?> response = actualState.updateCart(cart.get(), lineProduct);
        if(response.getStatusCodeValue() == 200){
            Cart cartUpdate = (Cart) response.getBody();
          
            cartRepository.save(cartUpdate);
            return new ResponseEntity<>(
                actualState.getCart(cartUpdate),
                HttpStatus.OK);
        }else{
            return response;
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
        asignStateCart(cart);
        return new ResponseEntity<>(actualState.getCart(cart), HttpStatus.OK);
    }

    private void createLinesCarts(Cart cart, List<FormCartProduct> lineProduct){
        Product product; 
        for (FormCartProduct productLine : lineProduct) {
            product = productRepository.findById(productLine.getId()).get();

            cart.getLineProducts().add(
                new LineProduct(productLine.getAmount(), product, cart)
            );
        }
        lineRepository.saveAll(cart.getLineProducts());
    }

    private boolean controlUniqueCartOpen(Client client){
        List<Cart> carts = new ArrayList<Cart>();
        carts = client.getCart();
        if(carts.size() != 0){
            carts = carts.stream().filter(
                cart -> cart.getEnumState() == EnumState.ACTIVE
            ).collect(Collectors.toList());
        }else{
            return false;
        }
        return (carts.size() > 0)?true:false;
    }

    private List<FormCartProduct> controlProducts(List<FormCartProduct> lineProduct){
        //Control de existencia del producto
        if(lineProduct.size() > 0){
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
        }
        return lineProduct;
        //Control del stock del producto.
    }

    private List<FormCartProduct> controlStockOfProducts(List<FormCartProduct> lineProduct){
        //Control de existencia del producto
        if(lineProduct.size() > 0){
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
        }
        return lineProduct;
        //Control del stock del producto.
    }

    private List<FormCartProduct> controlAmountOfProducts(List<FormCartProduct> lineProduct){
        if(lineProduct.size() > 0){
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
        }
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

    @Transactional
    @Override
    public ResponseEntity<?> getCart(Client cliente, EnumState state) {
        List<Cart> listCart = (List<Cart>) cartRepository.findAll();

        listCart = listCart.stream()
        .filter(cart -> cart.getBuyer().getId() == cliente.getId())
        .collect(Collectors.toList());

        if(state != null){
            listCart = listCart.stream()
            .filter(cart -> cart.getEnumState() == state)
            .collect(Collectors.toList());
        }
        
        return new ResponseEntity<>(constructorModelListCart(listCart), HttpStatus.OK);
    }

    private List<ModelListCart> constructorModelListCart(List<Cart> listCart){
        BuilderGetCartsImpl builder = new BuilderGetCartsImpl();
        List<ModelListCart> listModelCart = new ArrayList<ModelListCart>();

        for (Cart cart : listCart) {
            listModelCart.add(
                builder
                .setId(cart.getId())
                .setState(cart.getEnumState()) 
                .setQuantity(cart.getLineProducts())
                .setTotal(cart.getLineProducts()) 
                .setDetailCart(cart.getId())
                .BuilderGetCarts()
                    
            );
        }
        return listModelCart;
    }
    
    @Transactional
    @Override
    public ResponseEntity<?> getCartById(Long id,Client cliente) {
        Optional<Cart> cart = cartRepository.findById(id);
        if(cart.isPresent()){
            if(cart.get().getBuyer().getId() != cliente.getId())
            return new ResponseEntity<>("Cart is not exists", 
            HttpStatus.FORBIDDEN);

            asignStateCart(cart.get());

            return new ResponseEntity<>(actualState.getCart(cart.get()), 
            HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Cart is not exists", 
            HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    @Override
    public ResponseEntity<?> deleteCartById(Long id, Client client) {
        Optional<Cart> cart = cartRepository.findById(id);
        
        if(cart.isPresent()){
            if(cart.get().getBuyer().getId() != client.getId())
            return new ResponseEntity<>("Cart is not exists", 
            HttpStatus.FORBIDDEN);

            cartRepository.delete(cart.get());
            return new ResponseEntity<>("Deleted cart sucesfully!", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Cart is not exists", 
            HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    @Override
    public ResponseEntity<?> closeCart(Client client, Long id) {
        //Controlar si quiere actualizar el estado del carrito
        final ResponseEntity<?> messageCartisForbidden =
        new ResponseEntity<>("Cart is not found", 
        HttpStatus.FORBIDDEN);
        final ResponseEntity<?> messageCartIsnotexists =
        new ResponseEntity<>("Cart is not found", 
        HttpStatus.NOT_FOUND);

        Optional<Cart> cart = cartRepository.findById(id);
        
        if(!cart.isPresent()) return messageCartIsnotexists;
        if(cart.get().getBuyer().getId() != client.getId()) return messageCartisForbidden;

        asignStateCart(cart.get());   
        
        ResponseEntity<?> response = actualState.closeCart(cart.get());
        if(response.getStatusCodeValue() == 200){
            Invoice invoice = (Invoice) response.getBody();
            invoiceRepository.save(invoice);
            cartRepository.save(cart.get());
            
            ResponseEntity<?> modelInvoice = 
            invoiceService.getInvoiceByIdCart(client, invoice.getCart().getId());
            
            return new ResponseEntity<>(modelInvoice.getBody(), 
            HttpStatus.OK);
        }else{
            return response;
        }
        
        
    }

    @Transactional
    @Override
    public ResponseEntity<?> getCartByOpened(Client cliente) {
        List<Cart> listCarts = cliente.getCart().stream()
        .filter(cart -> cart.getEnumState() == EnumState.ACTIVE)
        .collect(Collectors.toList());

        if(listCarts.size() > 0){
            Long idCart = listCarts.get(0).getId();
            return new ResponseEntity<>("http://localhost:8080/api/v1/carts/"+idCart, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Already you haven't a cart active", 
            HttpStatus.OK);
        }
        
    }


}
