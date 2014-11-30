package org.coursera.capstone.mutibo.fausto85.client.user;

public class UserManager {

	private static UserManager instance;
	private static User mUser;
	
	public static UserManager getInstance(){
		if(instance == null){
			instance = new UserManager();
		}
		return instance;
	}

	private UserManager(){
	}
	
	public static void init(String username){
		instance = new UserManager(username);
	}

	private UserManager(String username){
		mUser = new User(username);
	}

	public User getUser(){
		return mUser;
	}
	
}
