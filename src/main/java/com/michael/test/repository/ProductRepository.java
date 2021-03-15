package com.michael.test.repository;

import com.michael.test.domains.Product;
import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * @author michaelwang on 2021-03-11
 */
public interface ProductRepository extends Repository<Product,Integer> {
    Product findByProductId(Integer productId);
    List<Product> findAll();
    void save(Product p);
}
