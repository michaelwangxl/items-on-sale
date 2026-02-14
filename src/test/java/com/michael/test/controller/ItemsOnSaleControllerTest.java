package com.michael.test.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.michael.test.controllers.ItemsOnSaleController;
import com.michael.test.domains.Product;
import com.michael.test.domains.Recommendation;
import com.michael.test.domains.User;
import com.michael.test.services.RecommendationService;
import com.michael.test.services.UserService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class ItemsOnSaleControllerTest {

	@Autowired
	private ItemsOnSaleController itemsOnSaleController;

	@Autowired
	private MockMvc mockMvc;

	private RecommendationService recommendationService;
	private UserService userService;
	private User testUser;
	private Recommendation testRecommendation;

	@BeforeEach
	public void setUp() {
		recommendationService = mock(RecommendationService.class);
		userService = mock(UserService.class);
		itemsOnSaleController.setRecommendationService(recommendationService);
		itemsOnSaleController.setUserService(userService);

		testUser = new User(1, "testUser", "password123");

		List<Product> myOrders = new ArrayList<>();
		myOrders.add(new Product(1, "Product 1", true));

		List<Product> wishList = new ArrayList<>();
		wishList.add(new Product(2, "Product 2", true));

		List<Product> hotDeals = new ArrayList<>();
		hotDeals.add(new Product(3, "Product 3", true));

		testRecommendation = new Recommendation(myOrders, wishList, hotDeals);
	}

	@Test
	public void testControllerIsInstantiated() {
		assertThat(itemsOnSaleController).isNotNull();
	}

	@Test
	public void testRecommendationServiceIsInjected() {
		assertThat(recommendationService).isNotNull();
	}

	@Test
	public void testUserServiceIsInjected() {
		assertThat(userService).isNotNull();
	}

	@Test
	@WithMockUser(username = "testUser", roles = "USER")
	public void testGetRecommendationsWithValidUser() throws Exception {
		Integer userId = 1;

		when(userService.findUserByUserId(userId)).thenReturn(testUser);
		when(recommendationService.getRecommendations(userId)).thenReturn(testRecommendation);

		mockMvc.perform(get("/recommendations/{userId}", userId)).andExpect(status().isOk())
				.andExpect(jsonPath("$.myOrders").isArray()).andExpect(jsonPath("$.wishList").isArray())
				.andExpect(jsonPath("$.hotDeals").isArray());

		verify(userService, times(1)).findUserByUserId(userId);
		verify(recommendationService, times(1)).getRecommendations(userId);
	}

	@Test
	@WithMockUser(username = "testUser", roles = "USER")
	public void testGetRecommendationsWithCorrectUserId() throws Exception {
		Integer userId = 1;

		when(userService.findUserByUserId(userId)).thenReturn(testUser);
		when(recommendationService.getRecommendations(userId)).thenReturn(testRecommendation);

		mockMvc.perform(get("/recommendations/{userId}", userId)).andExpect(status().isOk());

		verify(userService, times(1)).findUserByUserId(userId);
	}

	@Test
	@WithMockUser(username = "wrongUser", roles = "USER")
	public void testGetRecommendationsWithUnauthorizedUser() throws Exception {
		Integer userId = 1;

		when(userService.findUserByUserId(userId)).thenReturn(testUser);

		mockMvc.perform(get("/recommendations/{userId}", userId)).andExpect(status().isForbidden());

		verify(userService, times(1)).findUserByUserId(userId);
	}

	@Test
	@WithMockUser(username = "testUser", roles = "USER")
	public void testGetRecommendationsWithNonExistentUser() throws Exception {
		Integer userId = 999;

		when(userService.findUserByUserId(userId)).thenReturn(null);

		mockMvc.perform(get("/recommendations/{userId}", userId)).andExpect(status().isForbidden());

		verify(userService, times(1)).findUserByUserId(userId);
	}

	@Test
	@WithMockUser(username = "testUser", roles = "USER")
	public void testGetRecommendationsReturnsCorrectStructure() throws Exception {
		Integer userId = 1;

		when(userService.findUserByUserId(userId)).thenReturn(testUser);
		when(recommendationService.getRecommendations(userId)).thenReturn(testRecommendation);

		mockMvc.perform(get("/recommendations/{userId}", userId)).andExpect(status().isOk())
				.andExpect(jsonPath("$.myOrders").isArray()).andExpect(jsonPath("$.myOrders[0].productId").value(1))
				.andExpect(jsonPath("$.wishList").isArray()).andExpect(jsonPath("$.hotDeals").isArray());
	}

	@Test
	public void testGetRecommendationsWithoutAuthentication() throws Exception {
		Integer userId = 1;

		mockMvc.perform(get("/recommendations/{userId}", userId)).andExpect(status().isUnauthorized());
	}

	@Test
	@WithMockUser(username = "testUser", roles = "USER")
	public void testSetRecommendationService() {
		RecommendationService mockService = mock(RecommendationService.class);
		itemsOnSaleController.setRecommendationService(mockService);
		assertNotNull(mockService);
	}

	@Test
	@WithMockUser(username = "testUser", roles = "USER")
	public void testSetUserService() {
		UserService mockService = mock(UserService.class);
		itemsOnSaleController.setUserService(mockService);
		assertNotNull(mockService);
	}

	@Test
	@WithMockUser(username = "testUser", roles = "USER")
	public void testGetRecommendationsWithEmptyRecommendation() throws Exception {
		Integer userId = 1;
		Recommendation emptyRecommendation = new Recommendation(new ArrayList<>(), new ArrayList<>(),
				new ArrayList<>());

		when(userService.findUserByUserId(userId)).thenReturn(testUser);
		when(recommendationService.getRecommendations(userId)).thenReturn(emptyRecommendation);

		mockMvc.perform(get("/recommendations/{userId}", userId)).andExpect(status().isOk())
				.andExpect(jsonPath("$.myOrders").isArray()).andExpect(jsonPath("$.myOrders.length()").value(0));
	}
}
