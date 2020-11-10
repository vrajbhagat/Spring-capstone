package ca.sheridancollege.bhagatvr.controllers;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.text.DateFormatter;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.sheridancollege.bhagatvr.beans.Appointment;
import ca.sheridancollege.bhagatvr.beans.Day;
import ca.sheridancollege.bhagatvr.beans.Timeslot;
import ca.sheridancollege.bhagatvr.beans.User;
import ca.sheridancollege.bhagatvr.config.EmailSenderService;
import ca.sheridancollege.bhagatvr.repositories.AppointmentRepository;
import ca.sheridancollege.bhagatvr.repositories.ConfirmationTokenRepository;
import ca.sheridancollege.bhagatvr.repositories.DayRepository;
import ca.sheridancollege.bhagatvr.repositories.RoleRepository;
import ca.sheridancollege.bhagatvr.repositories.TimeslotRepository;
import ca.sheridancollege.bhagatvr.repositories.UserRepository;

import org.eclipse.jdt.core.compiler.InvalidInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
public class TimeSlotController {

	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private AppointmentRepository appointmentRepository;
	private TimeslotRepository timeslotRepository;
	private ConfirmationTokenRepository confirmationTokenRepository;
	private EmailSenderService emailSenderService;
	private DayRepository dayRepository;
	
	// Get TimeSlots according to Day
	@GetMapping(value = "/getTimeSlots/{year}/{month}/{day}")
	public Day getTimeSlots(@PathVariable String year, @PathVariable String month, @PathVariable String day) {
		final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		LocalDate newDate = LocalDate.parse(year + "-" + month + "-" + day, dtf);
		DayOfWeek dayOfWeek = newDate.getDayOfWeek();
		Day newDay = dayRepository.findByDayOfWeek(dayOfWeek);
		List<Timeslot> ts = newDay.getTs();
		List<Appointment> app = appointmentRepository.findTodayAppointment(newDate);

		for (int i = 0; i < app.size(); i++) {
			for (int j = 0; j < ts.size(); j++) {
				if (ts.get(j).getId() == app.get(i).getTime().getId()) {
					ts.remove(j);
					break;
				}
			}
		}
		newDay.setTs(ts);
		System.out.println(newDay.getDayOfWeek());
		return newDay;
	}

}
