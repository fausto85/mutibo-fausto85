package org.coursera.capstone.mutibo.fausto85.client.user;

import com.google.common.base.Objects;

public class User {

	public User() {
	}

	public User(String username) {
		super();
		this.username = username;
		this.points = 0;
	}

	public User(String username, int points) {
		super();
		this.username = username;
		this.points = points;
	}

	private long id;
	private String username;
	private long points;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public long getPoints() {
		return points;
	}

	public void setPoints(long points) {
		this.points = points;
	}

	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof User) {
			User other = (User) obj;
			return Objects.equal(username, other.username);
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(username);
	}
	
}
