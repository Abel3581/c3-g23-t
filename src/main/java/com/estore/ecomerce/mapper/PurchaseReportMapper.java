
package com.estore.ecomerce.mapper;


import com.estore.ecomerce.domain.Product;
import com.estore.ecomerce.domain.PurchaseReport;
import com.estore.ecomerce.dto.ModelPurchaseReport;
import com.estore.ecomerce.dto.PurchaseReportRequest;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class PurchaseReportMapper {
    public ModelPurchaseReport purchaseReportEntityDto(PurchaseReport entity) {
        ModelPurchaseReport reportResponse = new ModelPurchaseReport();
        reportResponse.setId(entity.getId());
        reportResponse.setQuantity(entity.getQuantity());     
              
        return reportResponse;
    }
    public PurchaseReport purchaseReportDtoEntity(PurchaseReportRequest request) {
        PurchaseReport report = new PurchaseReport();
        report.setQuantity(request.getQuantity());   
        report.setCreationDate(request.getCreationDate());
        
        return report;
    }
    public PurchaseReportRequest PurchaseReportRequest(Integer quantity, Product product) {
        PurchaseReportRequest report = new PurchaseReportRequest();        
        report.setQuantity(quantity);
         LocalDateTime dateTime=LocalDateTime.now();
        report.setCreationDate(Timestamp.valueOf(dateTime)); 
        report.setProduct(product);
        return report;
    }
}
