package org.coursera.capstone.mutibo.fausto85.server;

import java.util.Collection;

import org.coursera.capstone.mutibo.fausto85.client.MutiboInterface;
import org.coursera.capstone.mutibo.fausto85.server.repo.Trivia;
import org.coursera.capstone.mutibo.fausto85.server.repo.TriviaRepository;
import org.coursera.capstone.mutibo.fausto85.server.repo.TriviaUpdate;
import org.coursera.capstone.mutibo.fausto85.server.repo.TriviaUpdate.Rating;
import org.coursera.capstone.mutibo.fausto85.server.repo.User;
import org.coursera.capstone.mutibo.fausto85.server.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class UpdateService {

	@Autowired
	private UserRepository mUserRepository;
	
	@Autowired
	private TriviaRepository mTriviaRepository;
	
	private static final long DISLIKES_TO_DELETE = (UserTestDatabase.MAX_USERS / 10) * (-1);
	
	@RequestMapping(value=MutiboInterface.USER_UPDATE_SVC_PATH, method=RequestMethod.POST)
	public @ResponseBody boolean updateUser(@RequestBody User userFromRequest){
		Collection<User> users = mUserRepository.findByUsername(userFromRequest.getUsername());
		if(users != null && users.iterator()!=null){
			User userFromRepo = users.iterator().next();
			long newPoints = userFromRepo.getPoints() + userFromRequest.getPoints();
			userFromRepo.setPoints(newPoints);
			mUserRepository.save(userFromRepo);
			System.out.println("User with name: " + userFromRepo.getUsername() + " Points: " + userFromRepo.getPoints());
			return true;
		}
		return false;
	}

	@RequestMapping(value=MutiboInterface.TRIVIA_UPDATE_SVC_PATH, method=RequestMethod.POST)
	public @ResponseBody boolean updateTriviaRating(@RequestBody Collection<TriviaUpdate> triviaUpdates){
		if(triviaUpdates !=null){
			for(TriviaUpdate update : triviaUpdates){
				Trivia t = mTriviaRepository.findOne(update.getId());
				if(t !=null){
					if(update.getRating() == Rating.LIKE){
						//System.out.println("Trivia with Id: " + t.getId() + "LIKED");
						//long rating = t.getRates() + 1;
						//t.setRates(rating);
					}else if(update.getRating() == Rating.DISLIKE){
						System.out.println("Trivia with Id: " + t.getId() + " DISLIKED");
						long rating = t.getRates() - 1;
						t.setRates(rating);
					}else if(update.getRating() == Rating.SKIPPED){
						//Do nothing
					}
					if(needsToBeDeleted(t.getId()) == false){
						mTriviaRepository.save(t);
					}else{
						mTriviaRepository.delete(t.getId());
						System.out.println("Deleted trivia with Id: " + t.getId());
					}
				}
			}
			return true;
		}else{
			return false;
		}
		
	}
	
	private Boolean needsToBeDeleted(long id){
		Boolean delete = false;
		Trivia t = mTriviaRepository.findOne(id);
		if(t.getRates() < DISLIKES_TO_DELETE){
			delete = true;
		}
		return delete;
	}
	
	
}
