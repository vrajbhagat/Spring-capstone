package ca.sheridancollege.bhagatvr.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.sheridancollege.bhagatvr.beans.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

	List<Appointment> save(List<Appointment> appointmentList);




}
