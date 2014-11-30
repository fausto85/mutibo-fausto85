package org.coursera.capstone.mutibo.fausto85.server.repo;

public class TriviaUpdate {

	public enum Rating{
		LIKE,
		DISLIKE,
		SKIPPED
	}

	public TriviaUpdate() {
		super();
	}
	public TriviaUpdate(long id, Rating rating, long userId) {
		super();
		this.id = id;
		this.rating = rating;
		this.userId = userId;
	}

	private long id;
	private TriviaUpdate.Rating rating;
	private long userId;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public TriviaUpdate.Rating getRating() {
		return rating;
	}
	public void setRating(TriviaUpdate.Rating rating) {
		this.rating = rating;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
}
