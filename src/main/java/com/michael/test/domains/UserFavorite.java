package com.michael.test.domains;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author michaelwang on 2021-03-14
 */
@Entity
public class UserFavorite implements Serializable {

    @Id
    private Integer favId;
    private Integer userId;
    private Integer productId;

    public UserFavorite() {
    }

    public UserFavorite(Integer favId, Integer userId, Integer productId) {
        this.favId = favId;
        this.userId = userId;
        this.productId = productId;
    }

    public Integer getFavId() {
        return favId;
    }

    public void setFavId(Integer favId) {
        this.favId = favId;
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

    @Override
    public String toString() {
        return "UserFavorite{" +
                "favId=" + favId +
                ", userId=" + userId +
                ", productId=" + productId +
                '}';
    }
}
