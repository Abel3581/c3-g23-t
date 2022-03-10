package com.estore.ecomerce.controller;


import java.util.List;

import com.estore.ecomerce.domain.Client;
import com.estore.ecomerce.dto.forms.FormCartProduct;
import com.estore.ecomerce.dto.forms.FormLineProduct;
import com.estore.ecomerce.service.InvoiceService;
import com.estore.ecomerce.service.abstraction.IUserService;
import com.estore.ecomerce.service.cartService.CartService;
import com.estore.ecomerce.utils.enums.EnumState;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor

@RequestMapping("/api/v1/carts")
@Api(value = "Cart Controller", description = "Crud for carts")
public class CartController {
    private final CartService cartService;
    private final IUserService userService;
    private final InvoiceService invoiceService;

    @PostMapping
    @ApiOperation(value = "Create cart", notes = "Return cart" )
    public ResponseEntity<?> createCart(
        @RequestPart(value="cart", required=true) List<FormCartProduct> formLineCart) 
    throws NotFoundException{
        FormLineProduct formLineProduct = new FormLineProduct();
        formLineProduct.setLineProduct(formLineCart);

        Client client = (Client) userService.getInfoUser();

        ResponseEntity<?> response = cartService.createCart(client, formLineProduct.getLineProduct());
        return new ResponseEntity<>(response.getBody(), response.getStatusCode());
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update cart for id", notes = "Return cart updated" )
    public ResponseEntity<?> updateCart(
        @PathVariable(name = "id") Long id,
        @RequestPart(value="cart", required=true) List<FormCartProduct> formLineCart) 
    throws NotFoundException{
        FormLineProduct formLineProduct = new FormLineProduct();
        formLineProduct.setLineProduct(formLineCart);

        Client client = (Client) userService.getInfoUser();
        
        ResponseEntity<?> response = cartService.updateCart(client, id, 
        formLineProduct.getLineProduct());

        return new ResponseEntity<>(response.getBody(), response.getStatusCode());
    }

    @PutMapping("/{id}/close")
    @ApiOperation(value = "Close cart", notes = "Return cart closed" )
    public ResponseEntity<?> closeCart(@PathVariable(name = "id") Long id) 
    throws NotFoundException{
        Client client = (Client) userService.getInfoUser();

        ResponseEntity<?> response = cartService.closeCart(client,id);
        return new ResponseEntity<>(response.getBody(), response.getStatusCode());
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deleted cart", notes = "Return cart deleted" )
    public ResponseEntity<?> clearCart(@PathVariable(name = "id") Long id) 
    throws NotFoundException{
        Client client = (Client) userService.getInfoUser();

        ResponseEntity<?> response = cartService.deleteCartById(id,client);
        return new ResponseEntity<>(response.getBody(), response.getStatusCode());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get cart for id", notes = "Return cart" )
    public ResponseEntity<?> getCartById(@PathVariable(name = "id") Long id) 
    throws NotFoundException{
        Client client = (Client) userService.getInfoUser();

        ResponseEntity<?> response = cartService.getCartById(id,client);
        return new ResponseEntity<>(response.getBody(), response.getStatusCode());
    }

    @GetMapping("/active")
    @ApiOperation(value = "Active cart", notes = "Return cart" )
    public ResponseEntity<?> getCartOpened() 
    throws NotFoundException{
        Client client = (Client) userService.getInfoUser();

        ResponseEntity<?> response = cartService.getCartByOpened(client);
        return new ResponseEntity<>(response.getBody(), response.getStatusCode());
        
    }

    @GetMapping
    @ApiOperation(value = "GetCart ", notes = "Return cart" )
    public ResponseEntity<?> getCart(
        @RequestParam(value="state", required = false) EnumState state
    ) throws NotFoundException{
        Client client = (Client) userService.getInfoUser(); 

        ResponseEntity<?> response = cartService.getCart(client,state);
        return new ResponseEntity<>(response.getBody(), response.getStatusCode());
    }

    @GetMapping("/{id}/invoice")
    @ApiOperation(value = "Get invoice by id cart", notes = "Return cart" )
    public ResponseEntity<?> getInvoiceByIdCart(@PathVariable(name = "id") Long id) throws NotFoundException{
        Client client = (Client) userService.getInfoUser();
        
        ResponseEntity<?> response = invoiceService.getInvoiceByIdCart(client, id);
        return new ResponseEntity<>(response.getBody(), response.getStatusCode());
    }

}
