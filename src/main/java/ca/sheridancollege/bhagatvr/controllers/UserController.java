package ca.sheridancollege.bhagatvr.controllers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import ca.sheridancollege.bhagatvr.beans.Appointment;
import ca.sheridancollege.bhagatvr.beans.User;
import ca.sheridancollege.bhagatvr.config.UserExcelExporter;
import ca.sheridancollege.bhagatvr.repositories.TimeslotRepository;
import ca.sheridancollege.bhagatvr.repositories.UserRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
public class UserController {

	private UserRepository userRepository;
	private TimeslotRepository timeslotRepository;

	
	// Display User Index Page
	@GetMapping("/user")
	public String displayUserIndexPage (Model model, Authentication authentication) {
		String name = authentication.getName();

		User user1 = userRepository.findByEmail(name);

		List<String> roles = new ArrayList<String>();
		for (GrantedAuthority ga : authentication.getAuthorities()) {
			roles.add(ga.getAuthority());
		}
		model.addAttribute("name", name);
		model.addAttribute("roles", roles);
		model.addAttribute("user", new User());
		model.addAttribute("appointment", new Appointment());
		model.addAttribute("us", userRepository.findById(user1.getId()).get());
		model.addAttribute("userList", userRepository.findById(user1.getId()).get().getAppointmentList());
		model.addAttribute("timelist", timeslotRepository.listTimeSlot(true));
		return "user/index";
	}
	
	// Export User's 
	@GetMapping("/export-users")
	public void exportToExcelUser(HttpServletResponse response) throws IOException {
		response.setContentType("application/octet-stream");
		String headerKey = "Content-Disposition";

		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());
		String fileName = "users_" + currentDateTime + ".xlsx";

		String headerValue = "attachment; filename=" + fileName;

		response.setHeader(headerKey, headerValue);

		List<User> listusers = userRepository.findAll();

		UserExcelExporter excelExporter = new UserExcelExporter(listusers);
		excelExporter.export(response);

	}
	
	// display User Details 
	@GetMapping("/update-profile")
    public String displayUserProfile (Model model, User user, Authentication authentication) {
    	User user1 = userRepository.findByEmail(authentication.getName());
		model.addAttribute("us", userRepository.findById(user1.getId()).get());
    	return "user/updateProfile";
    }

}
