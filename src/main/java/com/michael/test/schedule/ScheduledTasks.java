package com.michael.test.schedule;

import com.michael.test.services.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author michaelwang on 2021-03-14
 */
@Component
public class ScheduledTasks {

  private RecommendationService recommendationService;

  @Autowired
  public void setRecommendationService(RecommendationService recommendationService) {
    this.recommendationService = recommendationService;
  }

  @Scheduled(cron = "0 0 0 ? * *") // At 00:00:00am every day
  public void changeOnSaleProducts() {
    recommendationService.updateOnSaleProducts();
  }
}
