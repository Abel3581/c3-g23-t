package com.estore.ecomerce.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.estore.ecomerce.domain.Cart;
import com.estore.ecomerce.domain.Client;
import com.estore.ecomerce.domain.Invoice;
import com.estore.ecomerce.dto.ModelDetailInvoice;
import com.estore.ecomerce.repository.CartRepository;
import com.estore.ecomerce.repository.InvoiceRepository;
import com.estore.ecomerce.utils.build.BuilderGetInvoiceByIdImpl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService{
    private final InvoiceRepository invoiceRepository;
    private final CartRepository cartRepository;

    @Transactional
    @Override
    public ResponseEntity<?> getInvoiceByIdCart(Client client, Long id) {
        Optional<Cart> cart = cartRepository.findById(id);

        final ResponseEntity<?> messageCartNotExists =
        new ResponseEntity<>("Cart is not exists", 
        HttpStatus.NOT_FOUND);

        final ResponseEntity<?> messageInvoiceForbiden =
        new ResponseEntity<>("The invoice is not exists", 
        HttpStatus.FORBIDDEN);

        final ResponseEntity<?> messageInvoiceNotGenerated =
        new ResponseEntity<>("The invoice not generated", 
        HttpStatus.FORBIDDEN);

        if(!cart.isPresent()) return messageCartNotExists;
        if(client.getId() != cart.get().getBuyer().getId()) return messageInvoiceForbiden;
        
        List<Invoice> invoices = (List<Invoice>) invoiceRepository.findAll();

        List<Invoice> invoicesRequest = invoices.stream()
        .filter(invoice -> invoice.getCart().getId() == id)
        .collect(Collectors.toList());

        if(invoicesRequest.size() == 0) return messageInvoiceNotGenerated;

        return new ResponseEntity<>(constructorInvoice(invoicesRequest.get(0)), 
        HttpStatus.OK);
    }

    private ModelDetailInvoice constructorInvoice(Invoice invoice){
        BuilderGetInvoiceByIdImpl builder = new BuilderGetInvoiceByIdImpl();
        ModelDetailInvoice modelDetailInvoice = new ModelDetailInvoice();

        modelDetailInvoice = builder.setId(invoice.getId())
        .setTotal(invoice)
        .setLineProduct(invoice)
        .setRegistration(invoice.getCreationDate())
        .builderGetInvoiceById();

        return modelDetailInvoice;
    }

    
}
