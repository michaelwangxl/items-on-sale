package com.michael.test.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.michael.test.controllers.HomeController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerTest {

	@Autowired
	private HomeController homeController;

	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	public void setUp() {
		assertThat(homeController).isNotNull();
	}

	@Test
	void contextLoads() {
		assertThat(homeController).isNotNull();
	}

	@Test
	@WithMockUser
	void testHomeEndpointReturnsOk() throws Exception {
		mockMvc.perform(get("/")).andExpect(status().isOk());
	}

	@Test
	void testHomeControllerIsInstantiated() {
		assertThat(homeController).isNotNull();
	}

	@Test
	void testHomeMethodReturnsString() {
		String result = homeController.Home();
		assertThat(result).isNotNull();
	}
}
