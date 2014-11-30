package org.coursera.capstone.mutibo.fausto85.server.repo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.common.base.Objects;
import com.google.gson.annotations.Expose;

@Entity
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

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String username;
	private long points;

	@Expose(serialize = false)
	private String gcmRegistrationId;
	@Expose(serialize = false)
	private long levelPlayed;
	
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGcmRegistrationId() {
		return gcmRegistrationId;
	}

	public void setGcmRegistrationId(String gcmRegistrationId) {
		this.gcmRegistrationId = gcmRegistrationId;
	}

	public long getLevelPlayed() {
		return levelPlayed;
	}

	public void setLevelPlayed(long levelPlayed) {
		this.levelPlayed = levelPlayed;
	}
	
}
