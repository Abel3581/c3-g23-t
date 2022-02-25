
package com.estore.ecomerce.mapper;


import com.estore.ecomerce.domain.Category;
import com.estore.ecomerce.domain.PurchaseReport;
import com.estore.ecomerce.dto.ModelPurchaseReport;
import com.estore.ecomerce.dto.PurchaseReportRequest;
import org.springframework.stereotype.Component;

@Component
public class PurchaseReportMapper {
    public ModelPurchaseReport purchaseReportEntityDto(PurchaseReport entity) {
        ModelPurchaseReport reportResponse = new ModelPurchaseReport();
        reportResponse.setId(entity.getId());
        reportResponse.setQuantity(entity.getQuantity());
        reportResponse.setCreationDate(entity.getCreationDate());
        return reportResponse;
    }
    public PurchaseReport PurchaseReportDtoEntity(PurchaseReportRequest request) {
        PurchaseReport report = new PurchaseReport();
        report.setId(request.getId());
        report.setQuantity(request.getQuantity());
        report.setCreationDate(request.getCreationDate());        
        return report;
    }
}
