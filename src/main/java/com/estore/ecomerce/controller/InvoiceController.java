package com.estore.ecomerce.controller;

import com.estore.ecomerce.domain.Client;
import com.estore.ecomerce.service.InvoiceService;
import com.estore.ecomerce.service.abstraction.IUserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/invoices")
public class InvoiceController {
    
    private final InvoiceService invoiceService;
    private final IUserService userService; 

    @GetMapping("/{id}")
    public ResponseEntity<?> getInvoiceByIdCart(@PathVariable(name = "id") Long id) throws NotFoundException{
        Client client = (Client) userService.getInfoUser();
        
        ResponseEntity<?> response = invoiceService.getInvoiceByIdCart(client, id);
        return new ResponseEntity<>(response.getBody(), response.getStatusCode());
    }

}
