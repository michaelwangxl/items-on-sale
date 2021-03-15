package com.michael.test.repository;

import com.michael.test.domains.UserOrder;
import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * @author michaelwang on 2021-03-14
 */
public interface UserOrderRepository extends Repository<UserOrder,Integer> {
    public List<UserOrder> findUserOrdersByUserId(Integer userId);
    public List<UserOrder> findUserOrdersByUserIdIsNot(Integer userId);
}
