package org.coursera.capstone.mutibo.fausto85.client.connection;

public class TriviaUpdate {

	public enum Rating{
		LIKE,
		DISLIKE,
		SKIPPED
	}

	public TriviaUpdate() {
		super();
	}
	public TriviaUpdate(Long id, Rating rating, long userId) {
		super();
		this.id = id;
		this.rating = rating;
		this.userId = userId;
	}

	private Long id;
	private TriviaUpdate.Rating rating;
	private long userId;

	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public TriviaUpdate.Rating getRating() {
		return rating;
	}
	public void setRating(TriviaUpdate.Rating rating) {
		this.rating = rating;
	}
	
}
