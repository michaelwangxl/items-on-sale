package com.michael.test.repository;

import com.michael.test.domains.UserOrder;
import java.util.List;
import org.springframework.data.repository.Repository;

/**
 * @author michaelwang on 2021-03-14
 */
public interface UserOrderRepository extends Repository<UserOrder, Integer> {
  public List<UserOrder> findUserOrdersByUserId(Integer userId);

  public List<UserOrder> findUserOrdersByUserIdIsNot(Integer userId);
}
