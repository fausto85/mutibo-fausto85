package org.coursera.capstone.mutibo.fausto85.client.question;

import java.util.ArrayList;
import java.util.Collection;
import org.coursera.capstone.mutibo.fausto85.client.connection.ServerConnectionManager;
import org.coursera.capstone.mutibo.fausto85.client.connection.TriviaUpdate;
import org.coursera.capstone.mutibo.fausto85.client.user.User;
import org.coursera.capstone.mutibo.fausto85.client.user.UserManager;

import android.util.Log;

public class AnswerManager {
	private static final String TAG = "QuestionManager";

	private static AnswerManager instance = null;
	
	private ServerConnectionManager mServerConnectionManager;
	private UserManager mUserManager;
	private Collection<Answer> mAnswers;
	private Collection<TriviaUpdate> mTrivias;
	//private static final Boolean TEST_OFFLINE = true;  
	private static final Boolean TEST_OFFLINE = false;  
	
	public static AnswerManager getInstance(){
		if(instance == null){
			instance = new AnswerManager();
		}
		return instance;
	}

	private AnswerManager() {
		mServerConnectionManager = ServerConnectionManager.getInstance();
		mUserManager = UserManager.getInstance();
		mAnswers = new ArrayList<Answer>();
		mTrivias = new ArrayList<TriviaUpdate>();
	}

	public void saveAnswer(Answer answer){
		mAnswers.add(answer);
		Log.d(TAG, "answer saved: " + answer.getTriviaId() + " - " + answer.getPoints());
	}
	
	public void saveTriviaUpdate(long triviaId, TriviaUpdate.Rating rating){
		TriviaUpdate triviaUpdate = new TriviaUpdate(triviaId, rating, mUserManager.getUser().getId());
		mTrivias.add(triviaUpdate);
		Log.d(TAG, "trivia update saved: " + triviaUpdate.getId() + " - " + triviaUpdate.getRating().toString());
	}

	public Boolean syncAnswers(){
		User userToUpdate = constructUserToUpdate();
		if(TEST_OFFLINE)
			return true;
		else
			return mServerConnectionManager.updatePointsToUser(userToUpdate);
	}

	public Boolean syncRatings(){
		if(TEST_OFFLINE)
			return true;
		else
			return mServerConnectionManager.updateTriviasRatings(mTrivias);
	}

	private User constructUserToUpdate() {
		User user = new User(UserManager.getInstance().getUser().getUsername());
		long points = 0;
		for(Answer a : mAnswers){
			points += a.getPoints();
		}
		user.setPoints(points);
		return user;
	}
	
	public void initPlaySession(){
		mAnswers = new ArrayList<Answer>();
		mTrivias = new ArrayList<TriviaUpdate>();
	}
}
