package ca.sheridancollege.bhagatvr.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
public class HomeController {
	
	@GetMapping("/") 
	public String home() {
		return "home";
	}

	
	@GetMapping("/access-denied")
	public String accessDenied() {
		return "error/access-denied";
	}
	
}
