package com.estore.ecomerce.utils.build;

import java.util.List;

import com.estore.ecomerce.domain.LineProduct;
import com.estore.ecomerce.dto.ModelListCart;
import com.estore.ecomerce.utils.enums.EnumState;

import org.apache.commons.math3.util.Precision;

public class BuilderGetCartsImpl implements BuilderGetCarts{
    private Long id;
    private EnumState state;
    private int quantity;
    private Double total;
    private String detailCart;

    


    /**
     * @param id the id to set
     */
    public BuilderGetCartsImpl setId(Long id) {
        this.id = id;
        return this;
    }


    /**
     * @param state the state to set
     */
    public BuilderGetCartsImpl setState(EnumState state) {
        this.state = state;
        return this;
    }

    /**
     * @param quantity the quantity to set
     */
    public BuilderGetCartsImpl setQuantity(List<LineProduct> lineProducts) {
        this.quantity = 0;
        for (LineProduct line : lineProducts) {
            this.quantity = this.quantity + 1;    
        }
        return this;
    }


    /**
     * @param total the total to set
     */
    public BuilderGetCartsImpl setTotal(List<LineProduct> lineProducts) {
        this.total = 0.0;
        Double finalPrice = 0.0;
        for (LineProduct line : lineProducts) {
            finalPrice = finalPrice + ((line.getProduct().getPrice() - (line.getProduct().getDiscount() / 100)*line.getProduct().getPrice())*line.getAmount());
        }
        this.total = Precision.round(finalPrice,2);
        return this;
    }

    /**
     * @param detailCart the detailCart to set
     */
    public BuilderGetCartsImpl setDetailCart(Long idCart) {
        String url = "http://localhost:8080/api/v1/carts/";
        this.detailCart = url+idCart.toString();
        return this;
    }



    @Override
    public ModelListCart BuilderGetCarts() {
        ModelListCart modelListCart = new ModelListCart();
        modelListCart.setId(this.id);
        modelListCart.setDetailCart(this.detailCart);
        modelListCart.setQuantity(this.quantity);
        modelListCart.setState(this.state);
        modelListCart.setTotal(this.total);
        
        return modelListCart;
    }

}
