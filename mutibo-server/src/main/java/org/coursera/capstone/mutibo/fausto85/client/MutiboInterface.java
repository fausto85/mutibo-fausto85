package org.coursera.capstone.mutibo.fausto85.client;

import java.util.Collection;

import org.coursera.capstone.mutibo.fausto85.server.repo.Trivia;
import org.coursera.capstone.mutibo.fausto85.server.repo.TriviaUpdate;
import org.coursera.capstone.mutibo.fausto85.server.repo.User;

import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

public interface MutiboInterface {
		public static final String PASSWORD_PARAMETER = "password";

		public static final String USERNAME_PARAMETER = "username";

		public static final String LOGIN_PATH = "/login";
		
		public static final String LOGOUT_PATH = "/logout";
		
		// The path where we expect the Trivia repo to live
		public static final String TRIVIA_SVC_PATH = "/trivia";

		// The path where we expect the User repo to live
		public static final String USER_SVC_PATH = "/user";

		// The path to update a User
		public static final String USER_UPDATE_SVC_PATH = "/updateuser";
		
		// The path to update a Trivia
		public static final String TRIVIA_UPDATE_SVC_PATH = "/updatetrivia";

		// The path to search videos by title
		public static final String USER_SEARCH_PATH = USER_SVC_PATH + "/search/findByUsername";

		//parameter to search by username
		public static final String USER_PARAMETER = "username";

		@FormUrlEncoded
		@POST(LOGIN_PATH)
		public Void login(@Field(USERNAME_PARAMETER) String username, @Field(PASSWORD_PARAMETER) String pass);
		
		@GET(LOGOUT_PATH)
		public Void logout();
				
		@GET(TRIVIA_SVC_PATH)
		public Collection<Trivia> getTriviaList();
		
		@POST(TRIVIA_SVC_PATH)
		public Void addTrivia(@Body Trivia v);
		
		@GET(USER_SEARCH_PATH)
		public Collection<User> findByUsername(@Query(USER_PARAMETER) String username);
		
		@POST(USER_UPDATE_SVC_PATH)
		public boolean updateUser(@Body User user);
		
		@POST(TRIVIA_UPDATE_SVC_PATH)
		public boolean updateTrivia(@Body Collection<TriviaUpdate> triviaUpdates);
}
