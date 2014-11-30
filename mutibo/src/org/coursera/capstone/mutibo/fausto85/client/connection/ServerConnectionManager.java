package org.coursera.capstone.mutibo.fausto85.client.connection;

import java.util.Collection;

import org.coursera.capstone.mutibo.fausto85.client.question.Trivia;
import org.coursera.capstone.mutibo.fausto85.client.user.User;
import org.coursera.capstone.mutibo.fausto85.client.user.UserManager;

import android.util.Log;

import retrofit.RestAdapter;
import retrofit.RestAdapter.LogLevel;
import retrofit.client.ApacheClient;

public class ServerConnectionManager {

	private static final String TAG = "ServerConnection";

	private static ServerConnectionManager instance = null;

	private RestAdapter mRestAdapter;
	private MutiboInterface mMutiboInterface; 
	private static final String mIP = "https://10.0.3.2:8443";
	
	public static ServerConnectionManager getInstance(){
		if(instance == null){
			instance = new ServerConnectionManager();
		}
		return instance;
	}
	
	//singleton
	private ServerConnectionManager(){
		try{
			mRestAdapter = new RestAdapter.Builder().setEndpoint(mIP).
					setClient(new ApacheClient(new EasyHttpClient())).
					setLogLevel(LogLevel.FULL).
					build();

			mMutiboInterface = mRestAdapter.create(MutiboInterface.class);
			
		}catch (Exception e){
			Log.d(TAG, "something went wrong with rest creation");
		}
	}
	
	public Boolean login(String email, String password){
		Boolean success = false;

		try{
			mMutiboInterface.login(email, password);
			success = true;
		}catch(Exception e){
			e.printStackTrace();
			Log.w(TAG, e.toString());
		}
		
		return success;
	}

	public Boolean logout(){
		Boolean success = false;
		try{
			mMutiboInterface.logout();
			success = true;
		}catch(Exception e){
			e.printStackTrace();
			Log.w(TAG, e.toString());
		}
		return success;
	}

	public Collection<Trivia> getTrivias(){
		Collection<Trivia> trivias = null;
		try{
			trivias = mMutiboInterface.getTriviaList();
		}catch(Exception e){
			e.printStackTrace();
			Log.w(TAG, e.toString());
		}
		return trivias;
	}

	public Boolean updatePointsToUser(User userToUpdate) {
		Boolean success = false;
		try{
			mMutiboInterface.updateUser(userToUpdate);
			success = true;
		}catch(Exception e){
			e.printStackTrace();
			Log.w(TAG, e.toString());
		}
		return success;
	}

	public Boolean updateTriviasRatings(Collection<TriviaUpdate> triviaUpdates) {
		Boolean success = false;
		try{
			mMutiboInterface.updateTrivia(triviaUpdates);
			success = true;
		}catch(Exception e){
			e.printStackTrace();
			Log.w(TAG, e.toString());
		}
		return success;
	}
	
	public User findCurrentUser(){
		Collection<User> users =  mMutiboInterface.findByUsername(UserManager.getInstance().getUser().getUsername());
		if(users!=null && users.iterator()!=null){
			return users.iterator().next();
		}else{
			Log.e(TAG, "user not found. Did we log in??");
			return null;
		}
	}
}
