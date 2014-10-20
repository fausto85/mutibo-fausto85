package org.coursera.capstone.mutibo.fausto85.question;

public class Set {
	
	public static final int NUMBER_OF_OPTIONS = 4;
	public static final int NUMBER_OF_HINTS = 4;

	private int mID = 0;
	private int mCorrectAnswer = 0;
	private Option[] mOptions;
	private String mRelationDescription = "";
	private String[] mHints;
	private int mDifficulty = 0;
	
	public Set(Set.Option[] options, int correctOption, String relationDescription){
		new Set(options, correctOption, relationDescription, 0);
	}

	public Set(Set.Option[] options, int correctOption, String relationDescription, int difficulty){
		new Set(options, correctOption, relationDescription, difficulty);
	}
	
	public Set(int id, Set.Option[] options, int correctOption, String relationDescription, 
			int difficulty, String[] hints){
		mOptions = new Set.Option[NUMBER_OF_OPTIONS];
		System.arraycopy(options, 0, mOptions, 0, NUMBER_OF_OPTIONS);
		mRelationDescription = relationDescription;
		mCorrectAnswer = correctOption;
		mDifficulty = difficulty;
		mHints = new String[NUMBER_OF_HINTS];
		System.arraycopy(hints, 0, mHints, 0, hints.length);
	}
	
	
	public int getmID() {
		return mID;
	}

	public Option[] getOptions(){
		return mOptions;
	}

	public int getmCorrectAnswer() {
		return mCorrectAnswer;
	}

	public String getmRelationDescription() {
		return mRelationDescription;
	}

	public String[] getmHints() {
		return mHints;
	}

	public int getmDifficulty() {
		return mDifficulty;
	}

		
	public static class Option{
	
		private String mText;
		
		public Option(String text){
			mText = text;
		}
		public String getText(){
			return mText;
		}
	}
	
}
