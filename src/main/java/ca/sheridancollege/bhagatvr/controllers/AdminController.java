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
	
    
    @GetMapping("/admin")
	public String adminHome() {
		return "admin/home";
	}
	
    @GetMapping("/findAllCustomer")
	public String findAllCustomer (Model model, @ModelAttribute User user, @ModelAttribute Appointment appointment) {
		List<User> userList = userRepository.findAll();
		model.addAttribute("userList", userList);
		model.addAttribute("user", new User());
		return "admin/customer";
	}
    
    
}
