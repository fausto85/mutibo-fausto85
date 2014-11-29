package org.coursera.capstone.mutibo.fausto85.client.question;

public class TestQuestions {

	private static String mMovieA1 = "A";
	private static String mMovieB1 = "B";
	private static String mMovieC1 = "C";
	private static String mMovieD1 = "D";
	private static long mOddMovie1 = 1;
	private static String mExplanation1 = "exp";
	private static long mRates1 = 0;
	private static long mLevel1 = 0;
	private static String mOddMovieString1 = "str";
	private static String mType1 = "genre";
	
	private static String mMovieA2 = "E";
	private static String mMovieB2 = "F";
	private static String mMovieC2 = "G";
	private static String mMovieD2 = "H";
	private static long mOddMovie2 = 1;
	private static String mExplanation2 = "exp";
	private static long mRates2 = 0;
	private static long mLevel2 = 0;
	private static String mOddMovieString2 = "str";
	private static String mType2 = "genre";
	
	public static final int TEST_NUMBER_OF_QUESTIONS = 2;
	public static Trivia[] trivias;
	
	
	public static void init(){
		trivias = new Trivia[TEST_NUMBER_OF_QUESTIONS];
		trivias[0] = new Trivia(mMovieA1, mMovieB1, mMovieC1, mMovieD1, 
					mOddMovie1, mExplanation1, mRates1, mLevel1, mOddMovieString1, mType1);
		trivias[1] = new Trivia(mMovieA2, mMovieB2, mMovieC2, mMovieD2, 
				mOddMovie2, mExplanation2, mRates2, mLevel2, mOddMovieString2, mType2);
	}
}
