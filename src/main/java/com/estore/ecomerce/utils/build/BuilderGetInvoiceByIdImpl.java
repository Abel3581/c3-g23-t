package com.estore.ecomerce.utils.build;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.estore.ecomerce.domain.Invoice;
import com.estore.ecomerce.domain.LineProduct;
import com.estore.ecomerce.dto.ModelDetailInvoice;
import com.estore.ecomerce.dto.ModelProductInvoice;

import org.apache.commons.math3.util.Precision;

public class BuilderGetInvoiceByIdImpl implements BuilderGetInvoiceById{
    private Long id;
    private LocalDateTime registration;
    private Double total; 
    private List<ModelProductInvoice> linesOfProducts;
    
    /**
     * @param id the id to set
     */
    public BuilderGetInvoiceByIdImpl setId(Long id) {
        this.id = id;
        return this;
    }

    /**
     * @param localDateTime the registration to set
     */
    public BuilderGetInvoiceByIdImpl setRegistration(LocalDateTime localDateTime) {
        this.registration = localDateTime;
        return this;
    }

    /**
     * @param total the total to set
     */
    public BuilderGetInvoiceByIdImpl setTotal(Invoice invoice) {
        this.total = 0.0;
        Double finalPrice = 0.0;
        for (LineProduct line : invoice.getCart().getLineProducts()) {
            finalPrice = finalPrice + (line.getProduct().getPrice() - 
                                      ((line.getProduct().getDiscount()/100)*line.getProduct().getPrice()))*line.getAmount();    
        }
        this.total = Precision.round(finalPrice,2);
        return this;
    }

    /**
     * @param lineProduct the lineProduct to set
     */
    public BuilderGetInvoiceByIdImpl setLineProduct(Invoice invoice) {
        Double price = 0.0;
        List<ModelProductInvoice> linesProducts = new ArrayList<ModelProductInvoice>();
        for (LineProduct line : invoice.getCart().getLineProducts()) {
            price = line.getProduct().getPrice()- ((line.getProduct().getDiscount()/100)*line.getProduct().getPrice()); 
            linesProducts.add(new ModelProductInvoice(
                line.getProduct().getName(),
                line.getAmount(),
                price
                )
            );
        }
        this.linesOfProducts = linesProducts;
        return this;
    }

    @Override
    public ModelDetailInvoice builderGetInvoiceById() {
        ModelDetailInvoice modelDetailInvoice = new ModelDetailInvoice();
        modelDetailInvoice.setId(this.id);
        modelDetailInvoice.setLineProduct(this.linesOfProducts);
        modelDetailInvoice.setRegistration(this.registration);
        modelDetailInvoice.setTotal(this.total);
        return modelDetailInvoice;
    }

}
