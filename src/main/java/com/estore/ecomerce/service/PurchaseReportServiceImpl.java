package com.estore.ecomerce.service;

import com.estore.ecomerce.domain.ImagePost;
import com.estore.ecomerce.domain.Product;
import com.estore.ecomerce.domain.PurchaseReport;
import com.estore.ecomerce.dto.ModelPurchaseReport;
import com.estore.ecomerce.dto.PurchaseReportRequest;
import com.estore.ecomerce.dto.forms.FormProduct;
import com.estore.ecomerce.mapper.PurchaseReportMapper;
import com.estore.ecomerce.repository.ProductRepository;
import com.estore.ecomerce.repository.PurchaseRepository;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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
    private ProductServiceImpl serviceImplProduct;
    private static final String ERROR_FIND_REPORT = "Error al solicitar reportes";
   
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
//           LocalDateTime dateTime=LocalDateTime.now(); 
           PurchaseReportRequest newReport = mapperPurchase.PurchaseReportRequest(quantity, product);   
           PurchaseReport report= mapperPurchase.purchaseReportDtoEntity(newReport); 
           PurchaseReport reportA=purchaseRepository.save(report);            
           List<PurchaseReport> list=product.getListReports();
           list.add(reportA);
           product.setListReports(list);  
           FormProduct productForm=new FormProduct();
         // serviceImplProduct.updateProduct(product, product.getId(), (ArrayList<ImagePost>) product.getImagePost(), product.getImageProfile());         
               
            }else  throw new ExceptionInInitializerError("Error al ingresar cantidad");
            
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(ERROR_FIND_REPORT);
        }
    }

}
