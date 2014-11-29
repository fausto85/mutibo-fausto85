package org.coursera.capstone.mutibo.fausto85.client.question;

import java.util.ArrayList;
import java.util.Collection;
import org.coursera.capstone.mutibo.fausto85.client.connection.ServerConnectionManager;

import android.util.Log;

public class AnswerManager {
	private static final String TAG = "QuestionManager";

	private static AnswerManager instance = null;
	
	private ServerConnectionManager mServerConnectionManager;
	private Collection<Answer> mAnswers;
	private static final Boolean TEST_OFFLINE = true;  
	//private static final Boolean TEST_OFFLINE = false;  
	
	public static AnswerManager getInstance(){
		if(instance == null){
			instance = new AnswerManager();
		}
		return instance;
	}

	private AnswerManager() {
		mAnswers = new ArrayList<Answer>();
	}

	public void saveAnswer(Answer answer){
		mAnswers.add(answer);
		Log.d(TAG, "answer saved: " + answer.getTriviaId() + " - " + answer.getPoints());
	}
	
	public Boolean syncAnswers(){
		if(TEST_OFFLINE)
			return true;
		else
			return mServerConnectionManager.syncAnswers(mAnswers);
	}
}
