package ca.sheridancollege.bhagatvr.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ca.sheridancollege.bhagatvr.beans.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
//	public User findByUsername(String username);	
	
	public User findByEmail(String email);
	
	User findByEmailIgnoreCase(String email);
	

}
