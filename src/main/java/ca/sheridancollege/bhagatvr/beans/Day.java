package ca.sheridancollege.bhagatvr.beans;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class Day {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private DayOfWeek dayOfWeek;
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<Timeslot> ts;

}
