package org.coursera.capstone.mutibo.fausto85;

import java.util.ArrayList;

import android.util.Log;

public class AuthenticationManager {

	private static final String TAG = "AuthenticationManager";

	//TODO: Only one account should be ever present
	private ArrayList<AuthenticationInfo> mAccounts;
	//private AuthenticationInfo mAccount;

	/**
	 * A dummy authentication store containing known user names and passwords.
	 * TODO: remove after connecting to a real authentication system.
	 */
	private static final String[] DUMMY_CREDENTIALS = new String[] {
		"foo@example.com", "12345678" };

	private static AuthenticationManager instance = null;
	
	public Boolean isUserRegistered(AuthenticationInfo account){
		//TODO: this should ask a web service
		Boolean registered = false;
		for ( AuthenticationInfo a : mAccounts ){
			if(a.getUser().equals(account.getUser()))
				registered = true;
		}
		return registered;
	}

	public Boolean registerUser(AuthenticationInfo account){
		Boolean registered = false;
		//TODO: this should send the information to a web service
		mAccounts.add(account);
		Log.i(TAG, "Registering Account");
		registered = true;
		Log.i(TAG, "Account Registered");
		//Log.w(TAG, "Account Registration Error");
		return registered;
	}
	
	public static AuthenticationManager getInstance(){
		if(instance == null){
			instance = new AuthenticationManager();
		}
		return instance;
	}
	
	public AuthenticationInfo getCurrentAccount(){
		if(mAccounts != null) return mAccounts.get(0);
		else return null;
			//TODO: Handle this like an exception
	}
	
	protected AuthenticationManager(){
		//Singleton
		AuthenticationInfo account =  new AuthenticationInfo();
		mAccounts = new ArrayList<AuthenticationInfo>();
		account.setUser(DUMMY_CREDENTIALS[0]);
		account.setPassword(DUMMY_CREDENTIALS[1]);
		mAccounts.add(account);
	}
}
