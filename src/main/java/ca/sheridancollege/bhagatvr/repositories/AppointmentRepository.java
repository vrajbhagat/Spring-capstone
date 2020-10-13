package ca.sheridancollege.bhagatvr.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ca.sheridancollege.bhagatvr.beans.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

	List<Appointment> save(List<Appointment> appointmentList);

	// Query for finding today appointment
	@Query(value = "SELECT * FROM Appointment a WHERE a.appointment_Date = ?1", nativeQuery = true)
	List<Appointment> findTodayAppointment(LocalDate appointmentDate);

	// Query for sort orderBy appointment Date
	public List<Appointment> findByOrderByAppointmentDate();

	// Query for sort orderBy appointment Time
	public List<Appointment> findByOrderByAppointmentTimeDesc();

}
