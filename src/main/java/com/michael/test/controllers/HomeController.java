package com.michael.test.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author michaelwang on 2021-03-13
 */
@RestController
public class HomeController {

	@GetMapping("/")
	public String Home() {
		return "";
	}
}
