package ca.sheridancollege.bhagatvr.controllers;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import ca.sheridancollege.bhagatvr.beans.Day;
import ca.sheridancollege.bhagatvr.beans.Timeslot;
import ca.sheridancollege.bhagatvr.repositories.DayRepository;
import ca.sheridancollege.bhagatvr.repositories.TimeslotRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
public class HomeController {
	
	private DayRepository dayRepository;
	private TimeslotRepository timeslotRepository;
	
	// Display Home Page
	@GetMapping("/")
	public String displayHomePage() {
		generateDefaultTimeSlots();
		return "home";
	}
	
	// Display Access Denied Page
	@GetMapping("/access-denied")
	public String displayAccessDeniedPage() {
		return "error/access-denied";
	}
	
	// Generate TimeSlots for Appointments
	private void generateDefaultTimeSlots() {
		LocalTime startTime = LocalTime.of(9, 0); // 9AM start time
		LocalTime endTime = LocalTime.of(19, 0); // 5PM end time

		for (int i = 1; i <= 7; i++) {
			List<Timeslot> timeSlots = new ArrayList<>();
			LocalTime currentTime = startTime.plusMinutes(15);
			while (currentTime.isBefore(endTime) && currentTime.isAfter(startTime)) {
				Timeslot newSlot = new Timeslot();
				newSlot.setTime(currentTime);
				newSlot = timeslotRepository.save(newSlot);
				timeSlots.add(newSlot);
				currentTime = currentTime.plusHours(1); // new slot after one hour
			}
			Day newDay = new Day();
			newDay.setTs(timeSlots);
			newDay.setDayOfWeek(DayOfWeek.of(i));
			dayRepository.save(newDay);
		}
	}

}
