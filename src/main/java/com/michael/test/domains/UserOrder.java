package com.michael.test.domains;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author michaelwang on 2021-03-10
 */
@Entity
public class UserOrder implements Serializable {
    @Id
    private Integer orderId;
    private Integer userId;
    private Integer productId;
    private Integer rating;

    public UserOrder() {
    }

    public UserOrder(Integer orderId, Integer userId, Integer productId, Integer rating) {
        this.orderId = orderId;
        this.userId = userId;
        this.productId = productId;
        this.rating = rating;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "UserOrder{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", productId=" + productId +
                ", rating=" + rating +
                '}';
    }
}
