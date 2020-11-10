package ca.sheridancollege.bhagatvr.controllers;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ca.sheridancollege.bhagatvr.beans.ConfirmationToken;
import ca.sheridancollege.bhagatvr.beans.User;
import ca.sheridancollege.bhagatvr.config.EmailSenderService;
import ca.sheridancollege.bhagatvr.repositories.ConfirmationTokenRepository;
import ca.sheridancollege.bhagatvr.repositories.RoleRepository;
import ca.sheridancollege.bhagatvr.repositories.UserRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
public class AuthenticationController {

	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private ConfirmationTokenRepository confirmationTokenRepository;
	private EmailSenderService emailSenderService;

	private String encodePassword(String password) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(password);
	}

	@GetMapping("/login")
	public String login() {
		return "login";

	}

	@RequestMapping(value = "/forgot-password", method = RequestMethod.GET)
	public ModelAndView displayResetPassword(ModelAndView modelAndView, User user) {
		modelAndView.addObject("user", user);
		modelAndView.setViewName("forgotPassword");
		return modelAndView;
	}

	@RequestMapping(value = "/forgot-password", method = RequestMethod.POST)
	public ModelAndView forgotUserPassword(ModelAndView modelAndView, User user) {
		User existingUser = userRepository.findByEmailIgnoreCase(user.getEmail());
		if (existingUser != null) {
			// Create token
			ConfirmationToken confirmationToken = new ConfirmationToken(existingUser);

			// Save it
			confirmationTokenRepository.save(confirmationToken);

			// Create the email
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setTo(existingUser.getEmail());
			mailMessage.setSubject("Complete Password Reset!");
			mailMessage.setFrom("Juicepetgrooming@gmail.com");
//			mailMessage.setText("To complete the password reset process, please click here: "
//					+ "https://spring-capstone.herokuapp.com/confirm-reset?token=" + confirmationToken.getConfirmationToken());
			mailMessage.setText("To complete the password reset process, please click here: "
					+ "http://localhost:8080/confirm-reset?token=" + confirmationToken.getConfirmationToken());

			// Send the email
			emailSenderService.sendEmail(mailMessage);

			modelAndView.addObject("message",
					"Request to reset password received. Check your inbox for the reset link.");
			modelAndView.setViewName("successForgotPassword");

		} else {
			modelAndView.addObject("message", "This email address does not exist!");
			modelAndView.setViewName("error");
		}
		return modelAndView;
	}

	@RequestMapping(value = "/confirm-reset", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView validateResetToken(ModelAndView modelAndView, @RequestParam("token") String confirmationToken) {
		ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

		if (token != null) {
			User user = userRepository.findByEmailIgnoreCase(token.getUser().getEmail());
			user.setEnabled(true);
			userRepository.save(user);
			modelAndView.addObject("user", user);
			modelAndView.addObject("email", user.getEmail());
			modelAndView.setViewName("resetPassword");
		} else {
			modelAndView.addObject("message", "The link is invalid or broken!");
			modelAndView.setViewName("error");
		}
		return modelAndView;
	}

	@RequestMapping(value = "/reset-password", method = RequestMethod.POST)
	public ModelAndView resetUserPassword(ModelAndView modelAndView, User user) {
		if (user.getEmail() != null) {
			// Use email to find user
			User tokenUser = userRepository.findByEmailIgnoreCase(user.getEmail());
			tokenUser.setEncryptedPassword(encodePassword(user.getEncryptedPassword()));
			userRepository.save(tokenUser);
			modelAndView.addObject("message",
					"Password successfully reset. You can now log in with the new credentials.");
			modelAndView.setViewName("successResetPassword");
		} else {
			modelAndView.addObject("message", "The link is invalid or broken!");
			modelAndView.setViewName("error");
		}
		return modelAndView;
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView displayRegistration(ModelAndView modelAndView, User user) {
		modelAndView.addObject("user", user);
		modelAndView.setViewName("register");
		return modelAndView;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView registerUser(ModelAndView modelAndView, @RequestParam String firstname,
			@RequestParam String lastname, @RequestParam String phonenumber, @RequestParam String email,
			@RequestParam String password) {
		User user = new User(firstname, lastname, phonenumber, email, encodePassword(password));
		user.setEnabled(false);
		User existingUser = userRepository.findByEmailIgnoreCase(user.getEmail());
		if (existingUser != null) {
			modelAndView.addObject("message", "This email already exists!");
			modelAndView.setViewName("error");
		} else {

			user.getRoles().add(roleRepository.findByRolename("ROLE_USER"));

			userRepository.save(user);

			ConfirmationToken confirmationToken = new ConfirmationToken(user);

			confirmationTokenRepository.save(confirmationToken);

			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setTo(user.getEmail());
			mailMessage.setSubject("Complete Registration!");
			mailMessage.setFrom("Juicepetgrooming@gmail.com");
//			mailMessage.setText("To confirm your account with JuicePet, please click here : "
//					+ "https://spring-capstone.herokuapp.com/confirm-account?token=" + confirmationToken.getConfirmationToken());
			mailMessage.setText("To confirm your account with JuicePet, please click here : "
					+ "http://localhost:8080/confirm-account?token=" + confirmationToken.getConfirmationToken());

			// https://spring-capstone.herokuapp.com

			emailSenderService.sendEmail(mailMessage);

			modelAndView.addObject("email", user.getEmail());

			modelAndView.setViewName("successfulRegisteration");
		}

		return modelAndView;
	}

	@RequestMapping(value = "/confirm-account", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView confirmUserAccount(ModelAndView modelAndView, @RequestParam("token") String confirmationToken) {
		ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

		if (token != null) {
			User user = userRepository.findByEmailIgnoreCase(token.getUser().getEmail());
			user.setEnabled(true);
			userRepository.save(user);
			modelAndView.setViewName("accountVerified");
		} else {
			modelAndView.addObject("message", "The link is invalid or broken!");
			modelAndView.setViewName("error");
		}

		return modelAndView;
	}

}
