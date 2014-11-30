package org.coursera.capstone.mutibo.fausto85.client.connection;

public class GCMUserRegistration {

	public GCMUserRegistration() {
		super();
	}
	public GCMUserRegistration(String username, String registrationId) {
		super();
		this.username = username;
		this.registrationId = registrationId;
	}

	private String username;
	private String registrationId;

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getRegistrationId() {
		return registrationId;
	}
	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
	}

}
