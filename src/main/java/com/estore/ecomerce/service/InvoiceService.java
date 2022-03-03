package com.estore.ecomerce.service;

import com.estore.ecomerce.domain.Client;

import org.springframework.http.ResponseEntity;

public interface InvoiceService {
    ResponseEntity<?> getInvoiceByIdCart(Client client, Long id);
}
