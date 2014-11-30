package org.coursera.capstone.mutibo.fausto85.server.repo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.common.base.Objects;

public class Answer {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private int points;
	private int triviaId;
	private Answer.Rating rating;

	public Answer() {
	}

	public Answer(int points, int triviaId) {
		this.points = points;
		this.triviaId = triviaId;
	}
	
	public Answer(int points, int triviaId, Answer.Rating rating) {
		this.points = points;
		this.triviaId = triviaId;
		this.setRating(rating);
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

	public Answer.Rating getRating() {
		return rating;
	}

	public void setRating(Answer.Rating rating) {
		this.rating = rating;
	}

	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Answer) {
			Answer other = (Answer) obj;
			return Objects.equal(id, other.id);
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id, triviaId, points);
	}
	
	public enum Rating{
		LIKE,
		DISLIKE,
		SKIPPED
	}

}
