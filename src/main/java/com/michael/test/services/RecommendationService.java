package com.michael.test.services;

import com.michael.test.domains.Product;
import com.michael.test.domains.Recommendation;
import com.michael.test.domains.UserFavorite;
import com.michael.test.domains.UserOrder;
import com.michael.test.repository.ProductRepository;
import com.michael.test.repository.UserFavoriteRepository;
import com.michael.test.repository.UserOrderRepository;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author michaelwang on 2021-03-10
 */
@Service
public class RecommendationService {

  private ProductRepository productRepository;
  private UserOrderRepository userOrderServiceRepository;
  private UserFavoriteRepository userFavoriteRepository;

  @Autowired
  public void setProductRepository(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Autowired
  public void setUserOrderServiceRepository(UserOrderRepository userOrderServiceRepository) {
    this.userOrderServiceRepository = userOrderServiceRepository;
  }

  @Autowired
  public void setUserFavoriteRepository(UserFavoriteRepository userFavoriteRepository) {
    this.userFavoriteRepository = userFavoriteRepository;
  }

  public Recommendation getRecommendations(Integer userId) {

    // my Orders List
    List<UserOrder> myOrders =
        userOrderServiceRepository.findUserOrdersByUserId(userId); // get orders based on userId.
    myOrders.sort(
        Comparator.comparing(UserOrder::getRating).reversed()); // sorting based on rating.(5->1)

    List<Product> myOrderList =
        myOrders.stream()
            .map(o -> productRepository.findByProductId(o.getProductId())) // map to products
            .filter(Product::isOnSale) // filter out only on-sale ones.
            .collect(Collectors.toList());

    // Wish List
    List<UserFavorite> myFav =
        userFavoriteRepository.findUserFavoriteByUserId(userId); // get favorites based userId.
    List<Product> wishList =
        myFav.stream()
            .map(f -> productRepository.findByProductId(f.getProductId())) // map to products
            .filter(Product::isOnSale) // filter out only on-sale ones.
            .collect(Collectors.toList());

    // Hot Deals
    List<UserOrder> othersOrders =
        userOrderServiceRepository.findUserOrdersByUserIdIsNot(
            userId); // get others' orders except userId.
    List<Product> hotDeals =
        othersOrders.stream()
            .filter(o -> o.getRating() == 5) // get highest rated products
            .map(o -> productRepository.findByProductId(o.getProductId())) // map to products
            .filter(Product::isOnSale) // filter out only on-sale ones.
            .collect(Collectors.toList());

    return new Recommendation(
        myOrderList, wishList, hotDeals); // put 3 categories to recommendation.
  }

  /** Randomly update on-sale products */
  public void updateOnSaleProducts() {
    List<Product> productList = productRepository.findAll();
    productList.forEach(
        p -> {
          p.setOnSale(new Random().nextBoolean());
          productRepository.save(p);
        });
  }
}
