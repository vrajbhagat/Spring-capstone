package ca.sheridancollege.bhagatvr.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ca.sheridancollege.bhagatvr.beans.Timeslot;

public interface TimeslotRepository extends JpaRepository<Timeslot, Long> {
	
	@Query(value="select * from Timeslot u where u.flag = ?1", nativeQuery=true)
	  List<Timeslot> listTimeSlot(Boolean s);
	
	@Query(value="select * from Timeslot u where u.timeselect = ?1", nativeQuery=true)
	  Timeslot updateTimeSlot(String s);

}
