package ca.sheridancollege.bhagatvr.beans;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class Appointment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate appointmentDate;

	private String appointmentTime;

	private String breed;

	private String services;

	@OneToOne
	private User user;
	
	@OneToOne
	private Timeslot time;

}
