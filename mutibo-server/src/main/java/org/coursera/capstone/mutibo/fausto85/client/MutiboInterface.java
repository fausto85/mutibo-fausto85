package org.coursera.capstone.mutibo.fausto85.client;

import java.util.Collection;

import org.coursera.capstone.mutibo.fausto85.server.repo.GCMUserRegistration;
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
		
		public static final String GCM_REGISTRATION_PATH = "/gcmRegistration";

		// The path where we expect the Trivia repo to live
		public static final String TRIVIA_SVC_PATH = "/trivia";

		// The path where we expect the User repo to live
		public static final String USER_SVC_PATH = "/user";

		// The path to update a User
		public static final String USER_UPDATE_SVC_PATH = "/updateuser";
		
		// The path to update a Trivia
		public static final String TRIVIA_UPDATE_SVC_PATH = "/updatetrivia";

		// The path to update a Trivia
		public static final String TRIVIA_ADDING_SVC_PATH = "/addtrivia";

		// The path to search users by username
		public static final String USER_SEARCH_PATH = USER_SVC_PATH + "/search/findByUsername";

		// The path to search users by username
		public static final String TRIVIAS_FOR_USER_PATH = "/triviasforuser";

		// The parameter to search trivias by level
		public static final String LEVEL_PARAMETER = "level";

		//parameter to search by username
		public static final String USER_PARAMETER = "username";

		@FormUrlEncoded
		@POST(LOGIN_PATH)
		public Void login(@Field(USERNAME_PARAMETER) String username, @Field(PASSWORD_PARAMETER) String pass);
		
		@GET(LOGOUT_PATH)
		public Void logout();
				
		@POST(GCM_REGISTRATION_PATH)
		public Void registerGCM(@Body GCMUserRegistration userRegistration);

		@GET(TRIVIA_SVC_PATH)
		public Collection<Trivia> getTriviaList();
		
		@POST(TRIVIA_SVC_PATH)
		public Void addTrivia(@Body Trivia v);
		
		@GET(TRIVIAS_FOR_USER_PATH)
		public Collection<Trivia> getTriviasForUserList(@Field(USERNAME_PARAMETER) String username);

		@GET(USER_SEARCH_PATH)
		public Collection<User> findByUsername(@Query(USER_PARAMETER) String username);
		
		@POST(USER_UPDATE_SVC_PATH)
		public boolean updateUser(@Body User user);
		
		@POST(TRIVIA_UPDATE_SVC_PATH)
		public boolean updateTrivia(@Body Collection<TriviaUpdate> triviaUpdates);
}
