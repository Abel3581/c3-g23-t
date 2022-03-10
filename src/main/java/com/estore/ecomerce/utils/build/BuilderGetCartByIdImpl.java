package com.estore.ecomerce.utils.build;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.estore.ecomerce.domain.Cart;
import com.estore.ecomerce.domain.Client;
import com.estore.ecomerce.domain.LineProduct;
import com.estore.ecomerce.dto.ModelClient;
import com.estore.ecomerce.dto.ModelDetailCart;
import com.estore.ecomerce.dto.ModelImage;
import com.estore.ecomerce.dto.ModelLineProduct;
import com.estore.ecomerce.utils.enums.EnumState;

import org.apache.commons.math3.util.Precision;

public class BuilderGetCartByIdImpl implements BuilderGetCartById{
    private Long id;
    private EnumState enumState;
    private LocalDateTime registration;
    private ModelClient client;
    private String optCloseCart;
    private String optCleanCart;
    private String invoice;
    private List<ModelLineProduct> lineProduct = new ArrayList<ModelLineProduct>();
    private double total;



    public BuilderGetCartByIdImpl setId(Long id) {
        this.id = id;
        return this;
    }


    public BuilderGetCartByIdImpl setEnumState(EnumState enumState) {
        this.enumState = enumState;
        return this;
    }


    public BuilderGetCartByIdImpl setRegistration(LocalDateTime registration) {
        this.registration = registration;
        return this;
    }



    public BuilderGetCartByIdImpl setClient(Client buyer) {
        
        if(buyer != null){
            String url = "http://localhost:8080/api/v1/client/";
            ModelClient clientRequest = new ModelClient(url+buyer.getId(),buyer.getName());
            this.client = clientRequest;
        }else{
            this.client = null;
        }
        
        return this;
    }


    public BuilderGetCartByIdImpl setLineProduct(Cart cart, List<LineProduct> lineProduct) {
        String nameImage;
        String urlImage;
        for (LineProduct line : lineProduct) {
            if(line.getProduct().getImageProfile() != null){
                nameImage = line.getProduct().getImageProfile().getName();
                urlImage = "http://localhost:8080/api/v1/images/profileimage/"+
                line.getProduct().getImageProfile().getId();
            }else{
                nameImage = "No Image";
                urlImage = "";
            }
            this.lineProduct.add(
                new ModelLineProduct(
                    line.getId(),
                    this.id,
                    line.getProduct().getName(),
                    new ModelImage(nameImage,urlImage),
                    line.getProduct().getPrice() - 
                    ((line.getProduct().getDiscount()/100)*line.getProduct().getPrice()),
                    line.getAmount(),
                    "http://localhost:8080/api/v1/products/"+line.getProduct().getId()
                )
            );
        }
        return this;
    }

    public BuilderGetCartByIdImpl setTotal(List<LineProduct> lineProduct) {
        this.total = 0.0;
        Double finalPrice = 0.0;
        for (LineProduct line : lineProduct) {
            finalPrice = finalPrice + 
            ((line.getProduct().getPrice() - 
            (line.getProduct().getDiscount() / 100)*line.getProduct().getPrice())*line.getAmount());
        }
        this.total = Precision.round(finalPrice,2);
        return this;
    }


    /**
     * @param optCloseCart the optCloseCart to set
     */
    public BuilderGetCartByIdImpl setOptCloseCart(Long idCart, EnumState state) {
        if(state == EnumState.ACTIVE){
            String url = "http://localhost:8080/api/v1/carts/";
            this.optCloseCart = url+idCart.toString()+"/close";
        }else{
            this.optCloseCart = null;
        }
        return this;
    }
    /**
     * @param optCloseCart the optCloseCart to set
     */
    public BuilderGetCartByIdImpl setOptCleanCart(Long idCart, EnumState state) {
        if(state == EnumState.ACTIVE){
            String url = "http://localhost:8080/api/v1/carts/";
            this.optCleanCart = url+idCart.toString();
        }else{
            this.optCleanCart = null;
        }
        return this;
    }

        /**
     * @param invoice the invoice to set
     */
    public BuilderGetCartByIdImpl setInvoice(Long idCart, EnumState state) {
        if(state == EnumState.CLOSED){
            String url = "http://localhost:8080/api/v1/carts/";
            this.invoice = url+idCart.toString()+"/invoice";
        }else{
            this.invoice = null;
        }

        return this;
    }

    @Override
    public ModelDetailCart builderGetCartById() {
        ModelDetailCart modelDetailCart = new ModelDetailCart();
        modelDetailCart.setClient(this.client);
        modelDetailCart.setEnumState(this.enumState);
        modelDetailCart.setId(this.id);
        modelDetailCart.setLineProduct(this.lineProduct);
        modelDetailCart.setRegistration(this.registration);
        modelDetailCart.setOptCloseCart(this.optCloseCart);
        modelDetailCart.setOptCleanCart(this.optCleanCart);
        modelDetailCart.setInvoice(this.invoice);
        modelDetailCart.setTotal(this.total);
        return modelDetailCart;
    }
    
}
