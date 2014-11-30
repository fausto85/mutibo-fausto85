package org.coursera.capstone.mutibo.fausto85.server;

import java.util.ArrayList;
import java.util.List;

import org.coursera.capstone.mutibo.fausto85.server.repo.User;

public class UserTestDatabase {

	public static List<User> users;
	public static List<String> passwords;
	public static final int MAX_USERS = 20;

	public static final String adminRole = "admin";
	public static final String userRole = "user";
	
	public static final String usernameBase = "user";
	public static final String passwordBase = "pass";

	public static final String usernameAdmin = "fausto85";
	public static final String passwordAdmin = "admin";
		
		public static void init(){
			users = new ArrayList<User>();
			passwords = new ArrayList<String>();
			for(int i = 0; i<MAX_USERS; i++){
				users.add(new User(usernameBase + i, 0));
				passwords.add(passwordBase + i);
			}
		}
	
}
