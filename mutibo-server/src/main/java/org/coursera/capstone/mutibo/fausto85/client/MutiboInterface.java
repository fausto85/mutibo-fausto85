package org.coursera.capstone.mutibo.fausto85.client;

import java.util.Collection;

import org.coursera.capstone.mutibo.fausto85.server.repo.Trivia;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;

public interface MutiboInterface {
		public static final String PASSWORD_PARAMETER = "password";

		public static final String USERNAME_PARAMETER = "username";

		public static final String LOGIN_PATH = "/login";
		
		public static final String LOGOUT_PATH = "/logout";
		
		// The path where we expect the MutiboSvc to live
		public static final String TRIVIA_SVC_PATH = "/trivia";

		@FormUrlEncoded
		@POST(LOGIN_PATH)
		public Void login(@Field(USERNAME_PARAMETER) String username, @Field(PASSWORD_PARAMETER) String pass);
		
		@GET(LOGOUT_PATH)
		public Void logout();
				
		@GET(TRIVIA_SVC_PATH)
		public Collection<Trivia> getTriviaList();
		
		@POST(TRIVIA_SVC_PATH)
		public Void addTrivia(@Body Trivia v);
}
