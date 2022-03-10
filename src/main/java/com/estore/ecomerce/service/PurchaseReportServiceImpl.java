package com.estore.ecomerce.service;


import com.estore.ecomerce.domain.Product;
import com.estore.ecomerce.domain.PurchaseReport;
import com.estore.ecomerce.dto.ModelPurchaseReport;
import com.estore.ecomerce.mapper.PurchaseReportMapper;
import com.estore.ecomerce.repository.ProductRepository;
import com.estore.ecomerce.repository.PurchaseRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.sql.Timestamp;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PurchaseReportServiceImpl implements PurchaseReportService {

    @Autowired
    private PurchaseRepository purchaseRepository;    
    @Autowired
    private PurchaseReportMapper mapperPurchase;
    @Autowired 
    private ProductRepository repositoryProduct; 
   
    private static final String ERROR_FIND_REPORT = "Error al solicitar reportes";
    private static final String ERROR_ADD_REPORT = "La cantidad no puede ser 0";
    @Transactional
    @Override
    public List<ModelPurchaseReport> findAll() {
        try {
            List<ModelPurchaseReport> listReport = new ArrayList<>();
            List<PurchaseReport> entities = purchaseRepository.findAll();
            for (PurchaseReport entity : entities) {
                listReport.add(mapperPurchase.purchaseReportEntityDto(entity));
            }
            return listReport;
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(ERROR_FIND_REPORT);
        }
    }
    @Transactional
    @Override
    public void savePurchaseReport(Integer quantity, Product product)  {
        try {
           if (quantity.intValue()>0) {
           PurchaseReport purchase = new PurchaseReport();
           purchase.setQuantity(quantity);
           LocalDateTime dateTime=LocalDateTime.now();
           purchase.setCreationDate(Timestamp.valueOf(dateTime));
           purchaseRepository.save(purchase);
           product.getListReports().add(purchase);
           repositoryProduct.save(product);
           
            }else  throw new ExceptionInInitializerError(ERROR_ADD_REPORT);
            
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(ERROR_FIND_REPORT);
        }
    }

}
