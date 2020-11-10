package ca.sheridancollege.bhagatvr.repositories;

import java.time.DayOfWeek;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.sheridancollege.bhagatvr.beans.Day;
import ca.sheridancollege.bhagatvr.beans.Timeslot;

public interface DayRepository extends JpaRepository<Day, Long> {

	Day findByDayOfWeek(DayOfWeek dayOfWeek);

}
