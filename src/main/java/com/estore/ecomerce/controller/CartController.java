package com.estore.ecomerce.controller;

import com.estore.ecomerce.domain.Client;
import com.estore.ecomerce.dto.forms.FormLineProduct;
import com.estore.ecomerce.service.InvoiceService;
import com.estore.ecomerce.service.abstraction.IUserService;
import com.estore.ecomerce.service.cartService.CartService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/carts")
public class CartController {
    private final CartService cartService;
    private final IUserService userService;
    private final InvoiceService invoiceService;

    @PostMapping
    public ResponseEntity<?> createCart(
        @RequestPart(value="cart", required=true) FormLineProduct formLineProduct) 
    throws NotFoundException{
        Client client = (Client) userService.getInfoUser();

        ResponseEntity<?> response = cartService.createCart(client, formLineProduct.getLineProduct());
        return new ResponseEntity<>(response.getBody(), response.getStatusCode());
    }

    @PutMapping("/{id}/close")
    public ResponseEntity<?> closeCart(@PathVariable(name = "id") Long id) 
    throws NotFoundException{
        Client client = (Client) userService.getInfoUser();

        ResponseEntity<?> response = cartService.closeCart(client,id);
        return new ResponseEntity<>(response.getBody(), response.getStatusCode());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCartById(@PathVariable(name = "id") Long id){
        ResponseEntity<?> response = cartService.getCartById(id);
        return new ResponseEntity<>(response.getBody(), response.getStatusCode());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCartById(@PathVariable(name = "id") Long id){
        ResponseEntity<?> response = cartService.deleteCartById(id);
        return new ResponseEntity<>(response.getBody(), response.getStatusCode());
    }

    @GetMapping("/{id}/invoice")
    public ResponseEntity<?> getInvoiceByIdCart(@PathVariable(name = "id") Long id) throws NotFoundException{
        Client client = (Client) userService.getInfoUser();
        
        ResponseEntity<?> response = invoiceService.getInvoiceByIdCart(client, id);
        return new ResponseEntity<>(response.getBody(), response.getStatusCode());
    }

}
