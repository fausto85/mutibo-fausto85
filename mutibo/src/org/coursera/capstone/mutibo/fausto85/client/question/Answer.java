package org.coursera.capstone.mutibo.fausto85.client.question;

public class Answer {

	private int points;
	private int triviaId;
	public static final int[] POINTS_PER_ANSWER = {5, 3, 1, 0};
	public static final int MAX_TRIES = 3;

	public Answer(int points, int triviaId) {
		this.points = points;
		this.triviaId = triviaId;
	}
	
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	public int getTriviaId() {
		return triviaId;
	}
	public void setTriviaId(int triviaId) {
		this.triviaId = triviaId;
	}

}
