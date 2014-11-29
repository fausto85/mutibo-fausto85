package org.coursera.capstone.mutibo.fausto85.server.repo;


import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.coursera.capstone.mutibo.fausto85.client.*;

@RepositoryRestResource(path = MutiboInterface.TRIVIA_SVC_PATH)
public interface TriviaRepository extends CrudRepository<Trivia, Long>{
}
