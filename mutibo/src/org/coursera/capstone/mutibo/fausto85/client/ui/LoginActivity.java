package org.coursera.capstone.mutibo.fausto85.client.ui;

import android.accounts.Account;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import org.coursera.capstone.mutibo.fausto85.R;
import org.coursera.capstone.mutibo.fausto85.client.AuthenticationInfo;
import org.coursera.capstone.mutibo.fausto85.client.AuthenticationManager;
import org.coursera.capstone.mutibo.fausto85.client.connection.ServerConnectionManager;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity implements LoaderCallbacks<Cursor> {

	/**
	 * Keep track of the login task to ensure we can cancel it if requested.
	 */
	private UserLoginTask mAuthTask = null;
	private static final String TAG = "LoginActity";
	// UI references.
	private AutoCompleteTextView mEmailView;
	private EditText mPasswordView;
	private View mProgressView;
	private View mLoginFormView;
	private AuthenticationManager mAuthManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); 
		StrictMode.setThreadPolicy(policy);		
		
		setContentView(R.layout.activity_login);
		
		mAuthManager = AuthenticationManager.getInstance();

		// Set up the login form.
		mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
		//populateAutoComplete();

		mPasswordView = (EditText) findViewById(R.id.password);
		mPasswordView
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView textView, int id,
							KeyEvent keyEvent) {
						if (id == R.id.login || id == EditorInfo.IME_NULL) {
							attemptLogin();
							return true;
						}
						return false;
					}
				});

		Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
		mEmailSignInButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				attemptLogin();
			}
		});

		mLoginFormView = findViewById(R.id.login_form);
		mProgressView = findViewById(R.id.login_progress);
	}

	@Override
	protected void onResume()	{
		super.onResume();
	}
	
	@Override
	protected void onRestart()	{
		super.onRestart();
	}
	
	private void populateAutoComplete() {
		getLoaderManager().initLoader(0, null, this);
	}

	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	public void attemptLogin() {
		Log.d(TAG, "Attempting login");

		if (mAuthTask != null) {
			return;
		}

		// Reset errors.
		mEmailView.setError(null);
		mPasswordView.setError(null);

		// Store values at the time of the login attempt.
		String email = mEmailView.getText().toString();
		String password = mPasswordView.getText().toString();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid password, if the user entered one.
		if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
			mPasswordView.setError(getString(R.string.error_invalid_password));
			focusView = mPasswordView;
			cancel = true;
		}

		// Check for a valid email address.
		if (TextUtils.isEmpty(email)) {
			mEmailView.setError(getString(R.string.error_field_required));
			focusView = mEmailView;
			cancel = true;
		} else if (!isEmailValid(email)) {
			mEmailView.setError(getString(R.string.error_invalid_email));
			focusView = mEmailView;
			cancel = true;
		}

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			showProgress(true);
			mAuthTask = new UserLoginTask(email, password);
			mAuthTask.execute((Void) null);
		}
	}

	private boolean isEmailValid(String email) {
		// TODO: Replace this with your own logic
		//return email.contains("@");
		return true;
	}

	private boolean isPasswordValid(String password) {
		// TODO: Replace this with your own logic
		return password.length() > 4;
	}

	public void showProgress(final boolean show) {
		int shortAnimTime = getResources().getInteger(
				android.R.integer.config_shortAnimTime);

		mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		mLoginFormView.animate().setDuration(shortAnimTime)
		.alpha(show ? 0 : 1)
		.setListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				mLoginFormView.setVisibility(show ? View.GONE
						: View.VISIBLE);
			}
		});

		mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
		mProgressView.animate().setDuration(shortAnimTime)
		.alpha(show ? 1 : 0)
		.setListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				mProgressView.setVisibility(show ? View.VISIBLE
						: View.GONE);
			}
		});
	}

	@Override
	public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
		return new CursorLoader(this,
				// Retrieve data rows for the device user's 'profile' contact.
				Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
						ContactsContract.Contacts.Data.CONTENT_DIRECTORY),
				ProfileQuery.PROJECTION,

				// Select only email addresses.
				ContactsContract.Contacts.Data.MIMETYPE + " = ?",
				new String[] { ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE },

				// Show primary email addresses first. Note that there won't be
				// a primary email address if the user hasn't specified one.
				ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
	}

	@Override
	public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
		List<String> emails = new ArrayList<String>();
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			emails.add(cursor.getString(ProfileQuery.ADDRESS));
			cursor.moveToNext();
		}

		addEmailsToAutoComplete(emails);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> cursorLoader) {

	}

	private interface ProfileQuery {
		String[] PROJECTION = { ContactsContract.CommonDataKinds.Email.ADDRESS,
				ContactsContract.CommonDataKinds.Email.IS_PRIMARY, };

		int ADDRESS = 0;
		int IS_PRIMARY = 1;
	}

	private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
		// Create adapter to tell the AutoCompleteTextView what to show in its
		// dropdown list.
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				LoginActivity.this,
				android.R.layout.simple_dropdown_item_1line,
				emailAddressCollection);

		mEmailView.setAdapter(adapter);
	}

	private void showRegistrationNotification() {
		Toast t = new Toast(getApplicationContext());
		t.setText(R.string.registration_being_done);
		t.show();
	}
	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

		private final String mEmail;
		private final String mPassword;
		private final ServerConnectionManager mServerConnectionManager;

		UserLoginTask(String email, String password) {
			mEmail = email;
			mPassword = password;
			mServerConnectionManager = ServerConnectionManager.getInstance();
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			Boolean success = false;
			
			try {
				success = mServerConnectionManager.login(mEmail, mPassword);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
//			AuthenticationInfo account  = new AuthenticationInfo();
//			account.setUser(mEmail);
//			account.setPassword(mPassword);
//			if(mAuthManager.isUserRegistered(account) == false){
//				Log.d(TAG, "User not registered");
//				// TODO: The registration should be done in another activity or by getting permission etc.
//				//showRegistrationNotification();
//				success = mAuthManager.registerUser(account);
//			} else {
//				Log.d(TAG, "User was already registered");
//				success = true;
//			}
				
			return success;
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			mAuthTask = null;
			showProgress(false);

			if (success) {
				Log.d(TAG, "Exiting Activity, going to Welcome Activity");
				Intent profileActivityIntent = new Intent(getApplicationContext(), WelcomeActivity.class);
				startActivity(profileActivityIntent);
			} else {
				mEmailView.setError(getString(R.string.error_invalid_email));
				mEmailView.requestFocus();
				mPasswordView
						.setError(getString(R.string.error_incorrect_password));
			}
		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
			showProgress(false);
		}
	}
}