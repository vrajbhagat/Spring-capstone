package ca.sheridancollege.bhagatvr.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ca.sheridancollege.bhagatvr.beans.Appointment;
import ca.sheridancollege.bhagatvr.beans.ConfirmationToken;
import ca.sheridancollege.bhagatvr.beans.User;
import ca.sheridancollege.bhagatvr.config.EmailSenderService;
import ca.sheridancollege.bhagatvr.repositories.AppointmentRepository;
import ca.sheridancollege.bhagatvr.repositories.ConfirmationTokenRepository;
import ca.sheridancollege.bhagatvr.repositories.RoleRepository;
import ca.sheridancollege.bhagatvr.repositories.TimeslotRepository;
import ca.sheridancollege.bhagatvr.repositories.UserRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
public class HomeController {
	
	private UserRepository userRepository; 
	private RoleRepository roleRepository;
	private AppointmentRepository appointmentRepository;
	private TimeslotRepository timeslotRepository;
    private ConfirmationTokenRepository confirmationTokenRepository;
    private EmailSenderService emailSenderService;
	
	private String encodePassword(String password) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); 
		return encoder.encode(password);
	}
	
	@GetMapping("/") 
	public String home() {
		return "home";
	}
	
	@GetMapping("/user") 
	public String userIndex(Model model, Authentication authentication) {
		String name = authentication.getName();
		
		User user1 = userRepository.findByEmail(name);
		
		List<String> roles = new ArrayList<String>();
		for (GrantedAuthority ga: authentication.getAuthorities()) {
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

	
	@GetMapping("/login")
	public String login() {
		
			return "login";
		
	}
	
	@GetMapping("/access-denied")
	public String accessDenied() {
		return "error/access-denied";
	}
	
//	
//	@GetMapping("/register") public String goRegistration () { return "register";
//	 }
//	 
//	 @PostMapping("/register") public String doRegistration(@RequestParam String
//	 firstname, @RequestParam String lastname, @RequestParam String
//	  phonenumber,@RequestParam String email, @RequestParam String password) { User
//	  user = new User(firstname, lastname, phonenumber, email,
//	  encodePassword(password), Byte.valueOf("1"));
//	  user.getRoles().add(roleRepository.findByRolename("ROLE_USER"));
//	  userRepository.save(user); return "redirect:/login"; }
	
	 @RequestMapping(value="/register", method = RequestMethod.GET)
	 public ModelAndView displayRegistration(ModelAndView modelAndView, User user)
	    {
	        modelAndView.addObject("user", user);
	        modelAndView.setViewName("register");
	        return modelAndView;
	    }
	 
	 @RequestMapping(value="/register", method = RequestMethod.POST)
	 public ModelAndView registerUser(ModelAndView modelAndView, @RequestParam String firstname, @RequestParam String lastname, @RequestParam String phonenumber,@RequestParam String email, @RequestParam String password) {	
		 User user = new User(firstname, lastname, phonenumber, email, encodePassword(password));
   	  		user.setEnabled(false);
	        User existingUser = userRepository.findByEmailIgnoreCase(user.getEmail());
	        if(existingUser != null)
	        {
	            modelAndView.addObject("message","This email already exists!");
	            modelAndView.setViewName("error");
	        }
	        else
	        {	
	      	  	
	        	user.getRoles().add(roleRepository.findByRolename("ROLE_USER"));
	        	
	            userRepository.save(user);

	            ConfirmationToken confirmationToken = new ConfirmationToken(user);

	            confirmationTokenRepository.save(confirmationToken);

	            SimpleMailMessage mailMessage = new SimpleMailMessage();
	            mailMessage.setTo(user.getEmail());
	            mailMessage.setSubject("Complete Registration!");
	            mailMessage.setFrom("Juicepetgrooming@gmail.com");
	            mailMessage.setText("To confirm your account with JuicePet, please click here : "
	            +"http://localhost:8080/confirm-account?token="+confirmationToken.getConfirmationToken());

	            emailSenderService.sendEmail(mailMessage);

	            modelAndView.addObject("email", user.getEmail());

	            modelAndView.setViewName("successfulRegisteration");
	        }

	        return modelAndView;
	    }
	 
	 @RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
	    public ModelAndView confirmUserAccount(ModelAndView modelAndView, @RequestParam("token")String confirmationToken)
	    {
	        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

	        if(token != null)
	        {
	            User user = userRepository.findByEmailIgnoreCase(token.getUser().getEmail());
	            user.setEnabled(true);
	            userRepository.save(user);
	            modelAndView.setViewName("accountVerified");
	        }
	        else
	        {
	            modelAndView.addObject("message","The link is invalid or broken!");
	            modelAndView.setViewName("error");
	        }

	        return modelAndView;
	    }
	 
	 	

	
	@PostMapping("/insertAppointments")
	public String insertAppointments (Model model, @ModelAttribute Appointment appointment, Authentication authentication, @RequestParam Long uid) {
		timeslotRepository.updateTimeSlot(appointment.getAppointmentTime()).setFlag(false);
		String name = authentication.getName();
		User user1 = userRepository.findByEmail(name);
		user1.getAppointmentList().add(appointment);
		userRepository.save(user1);
		model.addAttribute("name", name); 
		model.addAttribute("us", userRepository.findById(user1.getId()).get());
		model.addAttribute("userList", userRepository.findById(user1.getId()).get().getAppointmentList());
		model.addAttribute("user", new User());
		model.addAttribute("appointment", new Appointment());
		model.addAttribute("timelist", timeslotRepository.listTimeSlot(true));
		return "user/index";
	}
	
	@GetMapping("/viewAppointment")
	public String viewAppointment (Model model, @ModelAttribute User user, Authentication authentication, @ModelAttribute Appointment appointment ) {
		User user1 = userRepository.findByEmail(authentication.getName());
		model.addAttribute("us", userRepository.findById(user1.getId()).get());
		model.addAttribute("userList", userRepository.findById(user1.getId()).get().getAppointmentList());
		return "user/viewAppointment";
	}
	
	
	
}
