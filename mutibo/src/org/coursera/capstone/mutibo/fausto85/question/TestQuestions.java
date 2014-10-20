package org.coursera.capstone.mutibo.fausto85.question;

import org.coursera.capstone.mutibo.fausto85.question.Set.Option;

public class TestQuestions {

	private static final String TEST_OPTION_A = "A";
	private static final String TEST_OPTION_B = "B";
	private static final String TEST_OPTION_C = "C";
	private static final String TEST_OPTION_D = "D";
	private static final String TEST_OPTION_E = "E";
	private static final String TEST_OPTION_F = "F";
	private static final String TEST_OPTION_G = "G";
	private static final String TEST_OPTION_H = "H";
	
	public static final int TEST_NUMBER_OF_QUESTIONS = 2;
	private static final int QUESTION_1_CORRECT_ANSWER = 0;
	private static final int QUESTION_2_CORRECT_ANSWER = 2;
	
	public static Set[] questions;
	private static Set.Option[] mOptions;
	
	public static void init(){
		questions = new Set[TEST_NUMBER_OF_QUESTIONS];
		mOptions = new Set.Option[Set.NUMBER_OF_OPTIONS];
		for(int i = 0; i<TEST_NUMBER_OF_QUESTIONS; i++){
			setOptions(i);
			questions[i] = new Set(mOptions, 0, "");
		}
	}
	
	
	private static void setOptions(int i){
		if(i==0){
			mOptions[0] = new Set.Option(TEST_OPTION_A);
			mOptions[1] = new Set.Option(TEST_OPTION_B);
			mOptions[2] = new Set.Option(TEST_OPTION_C);
			mOptions[3] = new Set.Option(TEST_OPTION_D);
		}else if(i==1){
			mOptions[0] = new Set.Option(TEST_OPTION_E);
			mOptions[1] = new Set.Option(TEST_OPTION_F);
			mOptions[2] = new Set.Option(TEST_OPTION_G);
			mOptions[3] = new Set.Option(TEST_OPTION_H);
		}
	}
	
}
