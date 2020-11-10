package ca.sheridancollege.bhagatvr.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import ca.sheridancollege.bhagatvr.beans.Appointment;
import ca.sheridancollege.bhagatvr.beans.User;
import ca.sheridancollege.bhagatvr.repositories.UserRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
public class AdminController {
	
	private UserRepository userRepository; 
	
    
	// Display Admin Home Page
    @GetMapping("/admin")
	public String viewAdminHomePage() {
		return "admin/home";
	}
	
    // Find all Customers from Database
    @GetMapping("/findAllCustomer")
	public String findAllCustomerFromDB (Model model, @ModelAttribute User user, @ModelAttribute Appointment appointment) {
		List<User> userList = userRepository.findAll();
		model.addAttribute("userList", userList);
		model.addAttribute("user", new User());
		return "admin/customer";
	}
    
    
}
