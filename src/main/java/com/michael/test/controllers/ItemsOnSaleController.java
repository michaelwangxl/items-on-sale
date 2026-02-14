package com.michael.test.controllers;

import com.michael.test.domains.Recommendation;
import com.michael.test.domains.User;
import com.michael.test.exception.NoAccessException;
import com.michael.test.services.RecommendationService;
import com.michael.test.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author michaelwang on 2021-03-10
 */
@RestController
public class ItemsOnSaleController {

	private RecommendationService recommendationService;
	private UserService userService;

	@Autowired
	public void setRecommendationService(RecommendationService recommendationService) {
		this.recommendationService = recommendationService;
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * get the recommendation list for the user.
	 *
	 * @param userId
	 * @param authentication
	 * @return
	 */
	@CrossOrigin(origins = "shopping.rbc.com") // restrain the calling URL origin to shopping.rbc.com
	@GetMapping("/recommendations/{userId}")
	public ResponseEntity<Recommendation> getRecommendations(@PathVariable Integer userId,
			Authentication authentication) {
		User user = userService.findUserByUserId(userId); // get the User based on userId.
		UserDetails userDetails = (UserDetails) authentication.getPrincipal(); // get current user.
		if (user != null && userDetails.getUsername().equals(user.getUserName())) { // validate if the user is calling
																					// the correct api.
			return ResponseEntity.ok(recommendationService.getRecommendations(userId)); // get the recommendation list
		} else {
			throw new NoAccessException(userDetails.getUsername()); // throw 403 Error
		}
	}
}
