package ca.sheridancollege.bhagatvr.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ca.sheridancollege.bhagatvr.beans.Timeslot;
import ca.sheridancollege.bhagatvr.beans.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
//	public User findByUsername(String username);	
	
	public User findByEmail(String email);
	
	User findByEmailIgnoreCase(String email);
	
	@Query(value="select * from User u where u.email=?1 and u.is_Enabled = ?2", nativeQuery=true)
	  Boolean activate(String e, Boolean s);

}
