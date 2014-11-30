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
	public TriviaUpdate(Long id, Rating rating) {
		super();
		this.id = id;
		this.rating = rating;
	}

	private Long id;
	private TriviaUpdate.Rating rating;

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
