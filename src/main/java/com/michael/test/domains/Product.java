package com.michael.test.domains;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.io.Serializable;

/**
 * @author michaelwang on 2021-03-10
 */
@Entity
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private Integer productId;
    private String productName;
    private boolean onSale;


    public Product() {
    }

    public Product(Integer productId, String productName, boolean onSale) {
        this.productId = productId;
        this.productName = productName;
        this.onSale = onSale;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public boolean isOnSale() {
        return onSale;
    }

    public void setOnSale(boolean onSale) {
        this.onSale = onSale;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", onSale=" + onSale +
                '}';
    }
}
