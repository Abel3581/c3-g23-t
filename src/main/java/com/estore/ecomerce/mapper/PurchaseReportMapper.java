
package com.estore.ecomerce.mapper;


import com.estore.ecomerce.domain.Product;
import com.estore.ecomerce.domain.PurchaseReport;
import com.estore.ecomerce.dto.ModelPurchaseReport;
import com.estore.ecomerce.dto.PurchaseReportRequest;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class PurchaseReportMapper {
    public ModelPurchaseReport purchaseReportEntityDto(PurchaseReport entity) {
        ModelPurchaseReport reportResponse = new ModelPurchaseReport();
        reportResponse.setId(entity.getId());
        reportResponse.setQuantity(entity.getQuantity());
        reportResponse.setCreationDate(LocalDateTime.MIN);
//        reportResponse.setCreationDate(entity.getCreationDate());        
        return reportResponse;
    }
    public PurchaseReport purchaseReportDtoEntity(PurchaseReportRequest request) {
        PurchaseReport report = new PurchaseReport();
        report.setId(request.getId());
        report.setQuantity(report.getQuantity());
//        report.setCreationDate(request.getCreationDate());        
        report.setCreationDate(LocalDateTime.MIN);
        return report;
    }
    public PurchaseReportRequest PurchaseReportRequest(Integer quantity, Product product) {
        PurchaseReportRequest report = new PurchaseReportRequest();        
        report.setQuantity(quantity);
//        report.setCreationDate(LocalDateTime.now()); 
        report.setProduct(product);
        return report;
    }
}
