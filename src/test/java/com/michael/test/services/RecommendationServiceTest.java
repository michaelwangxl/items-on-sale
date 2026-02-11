package com.michael.test.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.michael.test.domains.Product;
import com.michael.test.repository.ProductRepository;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RecommendationServiceTest {

  @Mock private ProductRepository productRepository;

  @Test
  public void getRecommendationsTest() {

    when(productRepository.findAll()).thenReturn(Collections.emptyList());

    List<Product> all = productRepository.findAll();
    assertNotNull(all);
    assertTrue(all.isEmpty());
  }
}
