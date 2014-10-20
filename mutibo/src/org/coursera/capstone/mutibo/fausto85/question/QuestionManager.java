package org.coursera.capstone.mutibo.fausto85.question;

public class QuestionManager {
	private static final String TAG = "QuestionManager";

	private static QuestionManager instance = null;
	
	public static QuestionManager getInstance(){
		if(instance == null){
			instance = new QuestionManager();
		}
		return instance;
	}
	
	//singleton
	private QuestionManager(){
		
	}
	
	public void loadQuestions(){
		
	}
}
