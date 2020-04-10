package ca.sheridancollege.bhagatvr.repositories;

import org.springframework.data.repository.CrudRepository;

import ca.sheridancollege.bhagatvr.beans.ConfirmationToken;

public interface ConfirmationTokenRepository extends CrudRepository<ConfirmationToken, String>{
		
	ConfirmationToken findByConfirmationToken(String confirmationToken);
}
