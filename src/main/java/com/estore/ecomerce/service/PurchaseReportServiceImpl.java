package com.estore.ecomerce.service;


import com.estore.ecomerce.domain.Product;
import com.estore.ecomerce.domain.PurchaseReport;
import com.estore.ecomerce.dto.ModelPurchaseReport;
import com.estore.ecomerce.dto.ProductReportResponse;
import com.estore.ecomerce.dto.PurchaseReportRequest;
import com.estore.ecomerce.mapper.ProductReportMapper;
import com.estore.ecomerce.mapper.PurchaseReportMapper;
import com.estore.ecomerce.repository.ProductRepository;
import com.estore.ecomerce.repository.PurchaseRepository;
import java.util.ArrayList;
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
    private ProductReportMapper mapperProductReport;
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

           PurchaseReportRequest newReport = mapperPurchase.PurchaseReportRequest(quantity, product);   
           PurchaseReport report= mapperPurchase.purchaseReportDtoEntity(newReport); 
           PurchaseReport reportA=purchaseRepository.save(report);
           ProductReportResponse newProduct=mapperProductReport.productReportEntity2Dto(product);           
           List<PurchaseReport> list=newProduct.getListReports();
           list.add(reportA);
           newProduct.setListReports(list); 
           repositoryProduct.save(mapperProductReport.productReportDto2Entity(newProduct));
                  
            }else  throw new ExceptionInInitializerError(ERROR_ADD_REPORT);
            
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(ERROR_FIND_REPORT);
        }
    }

}
