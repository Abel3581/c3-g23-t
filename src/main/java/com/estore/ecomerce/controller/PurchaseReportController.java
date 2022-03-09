package com.estore.ecomerce.controller;

import com.estore.ecomerce.domain.Product;
import com.estore.ecomerce.service.ProductServiceImpl;
import com.estore.ecomerce.service.PurchaseReportServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/api/v1/purchasereport")
public class PurchaseReportController {

    @Autowired
    private PurchaseReportServiceImpl servicePurchase;
    @Autowired
    private ProductServiceImpl serviceProduct;
    @Autowired
    private PurchaseReportServiceImpl servicePurchaseReport;

    @GetMapping("")
    public ResponseEntity<?> findAll() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(servicePurchase.findAll());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }
    
    //Modo de prueba
    @GetMapping("/{id}")
    public String createReport(@PathVariable Long id) {
         Integer cantidad = 4;
        try {
            Product entityById = serviceProduct.productById(id);       
            servicePurchaseReport.savePurchaseReport(cantidad, entityById);
         
            return entityById.getName().toString();
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
