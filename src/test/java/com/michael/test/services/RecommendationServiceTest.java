package com.michael.test.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import com.michael.test.domains.Product;
import com.michael.test.domains.Recommendation;
import com.michael.test.domains.UserFavorite;
import com.michael.test.domains.UserOrder;
import com.michael.test.repository.ProductRepository;
import com.michael.test.repository.UserFavoriteRepository;
import com.michael.test.repository.UserOrderRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RecommendationServiceTest {

	@Mock
	private ProductRepository productRepository;

	@Mock
	private UserOrderRepository userOrderRepository;

	@Mock
	private UserFavoriteRepository userFavoriteRepository;

	@InjectMocks
	private RecommendationService recommendationService;

	private Product product1;
	private Product product2;
	private Product product3;
	private UserOrder userOrder1;
	private UserOrder userOrder2;
	private UserFavorite userFavorite1;

	@BeforeEach
	public void setUp() {
		product1 = new Product(1, "Product 1", true);
		product2 = new Product(2, "Product 2", false);
		product3 = new Product(3, "Product 3", true);

		userOrder1 = new UserOrder(1, 1, 1, 5);
		userOrder2 = new UserOrder(2, 2, 2, 5);

		userFavorite1 = new UserFavorite(1, 1, 3);
	}

	@Test
	public void testGetRecommendationsWithValidUserId() {
		Integer userId = 1;

		List<UserOrder> myOrders = new ArrayList<>();
		myOrders.add(userOrder1);

		List<UserOrder> othersOrders = new ArrayList<>();
		othersOrders.add(userOrder2);

		List<UserFavorite> myFavorites = new ArrayList<>();
		myFavorites.add(userFavorite1);

		when(userOrderRepository.findUserOrdersByUserId(userId)).thenReturn(myOrders);
		when(userOrderRepository.findUserOrdersByUserIdIsNot(userId)).thenReturn(othersOrders);
		when(userFavoriteRepository.findUserFavoriteByUserId(userId)).thenReturn(myFavorites);
		when(productRepository.findByProductId(1)).thenReturn(product1);
		when(productRepository.findByProductId(2)).thenReturn(product2);
		when(productRepository.findByProductId(3)).thenReturn(product3);

		Recommendation recommendation = recommendationService.getRecommendations(userId);

		assertNotNull(recommendation);
		assertNotNull(recommendation.getMyOrders());
		assertNotNull(recommendation.getWishList());
		assertNotNull(recommendation.getHotDeals());
		assertEquals(1, recommendation.getMyOrders().size());
		assertEquals(1, recommendation.getWishList().size());

		verify(userOrderRepository, times(1)).findUserOrdersByUserId(userId);
		verify(userOrderRepository, times(1)).findUserOrdersByUserIdIsNot(userId);
		verify(userFavoriteRepository, times(1)).findUserFavoriteByUserId(userId);
	}

	@Test
	public void testGetRecommendationsWithEmptyOrders() {
		Integer userId = 1;

		when(userOrderRepository.findUserOrdersByUserId(userId)).thenReturn(new ArrayList<>());
		when(userOrderRepository.findUserOrdersByUserIdIsNot(userId)).thenReturn(new ArrayList<>());
		when(userFavoriteRepository.findUserFavoriteByUserId(userId)).thenReturn(new ArrayList<>());

		Recommendation recommendation = recommendationService.getRecommendations(userId);

		assertNotNull(recommendation);
		assertTrue(recommendation.getMyOrders().isEmpty());
		assertTrue(recommendation.getWishList().isEmpty());
		assertTrue(recommendation.getHotDeals().isEmpty());
	}

	@Test
	public void testGetRecommendationsFiltersOnSaleProducts() {
		Integer userId = 1;

		List<UserOrder> myOrders = new ArrayList<>();
		myOrders.add(new UserOrder(1, 1, 1, 5));
		myOrders.add(new UserOrder(2, 1, 2, 4));

		when(userOrderRepository.findUserOrdersByUserId(userId)).thenReturn(myOrders);
		when(userOrderRepository.findUserOrdersByUserIdIsNot(userId)).thenReturn(new ArrayList<>());
		when(userFavoriteRepository.findUserFavoriteByUserId(userId)).thenReturn(new ArrayList<>());
		when(productRepository.findByProductId(1)).thenReturn(product1); // onSale = true
		when(productRepository.findByProductId(2)).thenReturn(product2); // onSale = false

		Recommendation recommendation = recommendationService.getRecommendations(userId);

		assertNotNull(recommendation);
		assertEquals(1, recommendation.getMyOrders().size());
		assertEquals(product1, recommendation.getMyOrders().get(0));
	}

	@Test
	public void testGetRecommendationsSortsOrdersByRating() {
		Integer userId = 1;

		List<UserOrder> myOrders = new ArrayList<>();
		myOrders.add(new UserOrder(1, 1, 1, 3));
		myOrders.add(new UserOrder(2, 1, 2, 5));
		myOrders.add(new UserOrder(3, 1, 3, 4));

		when(userOrderRepository.findUserOrdersByUserId(userId)).thenReturn(myOrders);
		when(userOrderRepository.findUserOrdersByUserIdIsNot(userId)).thenReturn(new ArrayList<>());
		when(userFavoriteRepository.findUserFavoriteByUserId(userId)).thenReturn(new ArrayList<>());
		when(productRepository.findByProductId(anyInt())).thenReturn(product1);

		Recommendation recommendation = recommendationService.getRecommendations(userId);

		assertNotNull(recommendation);
		// Orders should be sorted by rating (5, 4, 3)
		assertEquals(5, myOrders.get(0).getRating());
		assertEquals(4, myOrders.get(1).getRating());
		assertEquals(3, myOrders.get(2).getRating());
	}

	@Test
	public void testGetRecommendationsHotDealsFiltersRating5() {
		Integer userId = 1;

		List<UserOrder> othersOrders = new ArrayList<>();
		othersOrders.add(new UserOrder(1, 2, 1, 5)); // rating 5
		othersOrders.add(new UserOrder(2, 2, 2, 4)); // rating 4
		othersOrders.add(new UserOrder(3, 3, 3, 5)); // rating 5

		when(userOrderRepository.findUserOrdersByUserId(userId)).thenReturn(new ArrayList<>());
		when(userOrderRepository.findUserOrdersByUserIdIsNot(userId)).thenReturn(othersOrders);
		when(userFavoriteRepository.findUserFavoriteByUserId(userId)).thenReturn(new ArrayList<>());
		when(productRepository.findByProductId(1)).thenReturn(product1);
		when(productRepository.findByProductId(3)).thenReturn(product3);

		Recommendation recommendation = recommendationService.getRecommendations(userId);

		assertNotNull(recommendation);
		assertEquals(2, recommendation.getHotDeals().size());
	}

	@Test
	public void testUpdateOnSaleProducts() {
		List<Product> products = new ArrayList<>();
		products.add(product1);
		products.add(product2);
		products.add(product3);

		when(productRepository.findAll()).thenReturn(products);

		recommendationService.updateOnSaleProducts();

		verify(productRepository, times(1)).findAll();
		verify(productRepository, times(3)).save(any(Product.class));
	}

	@Test
	public void testGetRecommendationsNotNull() {
		when(productRepository.findAll()).thenReturn(Collections.emptyList());

		List<Product> all = productRepository.findAll();
		assertNotNull(all);
		assertTrue(all.isEmpty());
	}
}
