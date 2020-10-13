package ca.sheridancollege.bhagatvr.controllers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.bhagatvr.beans.Appointment;
import ca.sheridancollege.bhagatvr.beans.User;
import ca.sheridancollege.bhagatvr.config.AppointmentExcelExporter;
import ca.sheridancollege.bhagatvr.config.EmailSenderService;
import ca.sheridancollege.bhagatvr.repositories.AppointmentRepository;
import ca.sheridancollege.bhagatvr.repositories.TimeslotRepository;
import ca.sheridancollege.bhagatvr.repositories.UserRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
public class AppointmentController {

	private UserRepository userRepository;
	private AppointmentRepository appointmentRepository;
	private TimeslotRepository timeslotRepository;
	private EmailSenderService emailSenderService;

	@PostMapping("/insertAppointments")
	public String insertAppointments(Model model, @ModelAttribute Appointment appointment,
			Authentication authentication, @RequestParam Long uid) {
		timeslotRepository.updateTimeSlot(appointment.getAppointmentTime()).setFlag(false);
		String name = authentication.getName();
		User user1 = userRepository.findByEmail(name);
		appointment.setUser(user1);
		appointment = appointmentRepository.save(appointment);
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
	public String viewAppointment(Model model, @ModelAttribute User user, Authentication authentication,
			@ModelAttribute Appointment appointment) {
		User user1 = userRepository.findByEmail(authentication.getName());
		model.addAttribute("us", userRepository.findById(user1.getId()).get());
		model.addAttribute("userList", userRepository.findById(user1.getId()).get().getAppointmentList());
		return "user/viewAppointment";
	}

	@GetMapping("/findAllAppointment")
	public String findAllAppointment(Model model, @ModelAttribute User user, @ModelAttribute Appointment appointment) {
		List<Appointment> appointmentList = appointmentRepository.findAll();
		model.addAttribute("appointmentList", appointmentList);
		model.addAttribute("appointment", new Appointment());
		return "admin/appointment";
	}

	@GetMapping("/findTodayAppointment")
	public String findTodayAppointment(Model model) {
		List<Appointment> appointmentList = appointmentRepository.findTodayAppointment(LocalDate.now());
		model.addAttribute("appointmentList", appointmentList);
		return "admin/appointment";
	}

	// Query for sort Date
	@GetMapping("/findByOrderByAppointmentDate")
	public String findByOrderByAppointmentDate(Model model) {
		List<Appointment> appointmentList = appointmentRepository.findByOrderByAppointmentDate();
		model.addAttribute("appointmentList", appointmentList);
		return "admin/appointment";
	}

	// Query for sort Date
	@GetMapping("/findByOrderByAppointmentTimeDesc")
	public String findByOrderByAppointmentTimeDesc(Model model) {
		List<Appointment> appointmentList = appointmentRepository.findByOrderByAppointmentTimeDesc();
		model.addAttribute("appointmentList", appointmentList);
		return "admin/appointment";
	}

	@GetMapping("/export-appointments")
	public void exportToExcelAppointment(HttpServletResponse response) throws IOException {
		response.setContentType("application/octet-stream");
		String headerKey = "Content-Disposition";

		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());
		String fileName = "appointments_" + currentDateTime + ".xlsx";

		String headerValue = "attachment; filename=" + fileName;

		response.setHeader(headerKey, headerValue);

		List<Appointment> listappointments = appointmentRepository.findAll();

		AppointmentExcelExporter excelExporter = new AppointmentExcelExporter(listappointments);
		excelExporter.export(response);
	}

	@GetMapping("/deleteAppointment/{id}")
	public String deleteAppointment(Model model, @PathVariable Long id) {
		User user = userRepository.findById(appointmentRepository.findById(id).get().getUser().getId()).get();
		List<Appointment> appointmentList = user.getAppointmentList();

		for (int i = 0; i < appointmentList.size(); i++) {
			if (appointmentList.get(i).getId() == id) {
				appointmentList.remove(i);
				break;
			}

		}

		userRepository.save(user);
		appointmentRepository.deleteById(id);
		model.addAttribute("appointmentList", appointmentRepository.findAll());
		model.addAttribute("appointment", new Appointment());

		User existingUser = userRepository.findByEmailIgnoreCase(user.getEmail());

		// Create the email
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(existingUser.getEmail());
		mailMessage.setSubject("Appointment Cancelled!");
		mailMessage.setFrom("Juicepetgroomin@gmail.com");
		mailMessage.setText("Your appointment has been successfully cancelled. To Reschedule the appointment, "
				+ "please visit our website. Thank you:)");

		// Send the email
		emailSenderService.sendEmail(mailMessage);

		return "admin/appointment";
	}
}
