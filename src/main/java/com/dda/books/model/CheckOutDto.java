package com.dda.books.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class CheckOutDto implements Serializable {

    private BigDecimal totalAmount=new BigDecimal(0);

    private BigDecimal discountedPrice=new BigDecimal(0);

    private BigDecimal totalDiscount=new BigDecimal(0);

    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(BigDecimal discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public BigDecimal getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(BigDecimal totalDiscount) {
        this.totalDiscount = totalDiscount;
    }
}
