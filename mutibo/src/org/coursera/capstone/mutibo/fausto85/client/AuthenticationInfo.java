package org.coursera.capstone.mutibo.fausto85.client;

public class AuthenticationInfo {

	private String mUser;

	public String getUser() {
		return mUser;
	}

	public void setUser(String user) {
		this.mUser = user;
	}

	public String getPassword() {
		return mPassword;
	}

	public void setPassword(String password) {
		this.mPassword = password;
	}

	private String mPassword;
	
	private Boolean mUserAuthenticated;

	public Boolean getUserAuthenticated() {
		return mUserAuthenticated;
	}

	public void setUserAuthenticated(Boolean userAuthenticated) {
		this.mUserAuthenticated = userAuthenticated;
	}
	
	
}
