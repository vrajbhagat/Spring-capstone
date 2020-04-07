package ca.sheridancollege.bhagatvr.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.sheridancollege.bhagatvr.beans.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	public Role findByRolename(String rolename);

}
