package org.coursera.capstone.mutibo.fausto85.server.repo;


import java.util.Collection;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.coursera.capstone.mutibo.fausto85.client.*;

@RepositoryRestResource(path = MutiboInterface.TRIVIA_SVC_PATH)
public interface TriviaRepository extends CrudRepository<Trivia, Long>{

	// Find all trivias with a matching level 
	public Collection<Trivia> findByLevel(

			@Param(MutiboInterface.LEVEL_PARAMETER) long level);

}
