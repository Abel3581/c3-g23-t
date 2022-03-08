
package com.estore.ecomerce.controller;

import com.estore.ecomerce.domain.Product;
import com.estore.ecomerce.domain.PurchaseReport;
import com.estore.ecomerce.service.PurchaseReportServiceImpl;
import java.net.URISyntaxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/api/v1/purchasereport")
public class PurchaseReportController {
    @Autowired 
     private     PurchaseReportServiceImpl servicePurchase;
    
    @GetMapping("")
    public ResponseEntity<?> findAll() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(servicePurchase.findAll());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }
    @PostMapping("")
    public String createCategory(
            @RequestParam (value = "cantidad", required = false) Integer report,
            @RequestPart  (value = "producto", required = false) Product product)
            throws URISyntaxException{
           
       servicePurchase.savePurchaseReport(report, product);
        return "ok";
    }
            
}
