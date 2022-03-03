
package com.estore.ecomerce.service;

import com.estore.ecomerce.domain.Product;
import com.estore.ecomerce.dto.ModelPurchaseReport;
import com.estore.ecomerce.dto.PurchaseReportRequest;
import java.util.List;



public interface PurchaseReportService {
     List<ModelPurchaseReport> findAll();
     void savePurchaseReport(Integer quantity, Product product);
}
