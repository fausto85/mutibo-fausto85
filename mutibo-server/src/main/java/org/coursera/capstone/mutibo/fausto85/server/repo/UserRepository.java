package org.coursera.capstone.mutibo.fausto85.server.repo;


import java.util.Collection;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.coursera.capstone.mutibo.fausto85.client.*;

@RepositoryRestResource(path = MutiboInterface.USER_SVC_PATH)
public interface UserRepository extends CrudRepository<User, Long>{

	// Find all users with a matching username (e.g., User.username)
	public Collection<User> findByUsername(

			@Param(MutiboInterface.USER_PARAMETER) String username);

}
