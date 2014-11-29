package org.coursera.capstone.mutibo.fausto85.client.question;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.coursera.capstone.mutibo.fausto85.client.connection.ServerConnectionManager;

import android.util.Log;

public class QuestionManager {
	private static final String TAG = "QuestionManager";

	private static QuestionManager instance = null;
	
	private ServerConnectionManager mServerConnectionManager;
	private Collection<Trivia> mTrivias;
	private Iterator<Trivia> mIterator;
	//private static final Boolean TEST_OFFLINE = true;  
	private static final Boolean TEST_OFFLINE = false;  
	
	public static QuestionManager getInstance(){
		if(instance == null){
			instance = new QuestionManager();
		}
		return instance;
	}
	
	//singleton
	private QuestionManager(){
		if(TEST_OFFLINE){
			TestQuestions.init();
		}else{
			mServerConnectionManager = ServerConnectionManager.getInstance();
		}
	}
	
	public Boolean loadQuestions(){
		if(TEST_OFFLINE == false)
			return loadQuestionsOnline();
		else
			return loadQuestionsOffline();
	}
	
	private Boolean loadQuestionsOffline() {
		Boolean success = false;
		TestQuestions.init();
		mTrivias = new ArrayList<Trivia>();
		//mTrivias.add();
		return success;
	}

	private Boolean loadQuestionsOnline(){
		Boolean success = false;
		mTrivias = mServerConnectionManager.getTrivias();
		if(mTrivias != null){
			mIterator = mTrivias.iterator();
			success = true;
			Log.w(TAG, "trivias loaded successfully");
		}
		else{
			Log.w(TAG, "trivias loading failed");
		}
		return success;
	}
	
	public Trivia getNextTrivia(){
		Trivia trivia = null; 
		if(mTrivias !=null){
			try {
				trivia = mIterator.next();
			} catch (NoSuchElementException e) {
				Log.d(TAG, "trivias end reached");
			} catch (Exception e2){
				
			}
		}
		return trivia;
	}
}
