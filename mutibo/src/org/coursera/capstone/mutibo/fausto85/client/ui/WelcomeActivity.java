package org.coursera.capstone.mutibo.fausto85.client.ui;

import org.coursera.capstone.mutibo.fausto85.R;
import org.coursera.capstone.mutibo.fausto85.client.connection.ServerConnectionManager;
import org.coursera.capstone.mutibo.fausto85.client.user.User;
import org.coursera.capstone.mutibo.fausto85.client.user.UserManager;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class WelcomeActivity extends Activity {

	private static final String TAG = "WelcomeActity";
	private TextView mTitleText;
	private TextView mTextCurrentScore;
	private Button mPlayButton;
	private ServerConnectionManager mServerConnectionManager;
	private UserPointsRetrievingTask mPointsRetrievingTask;
	private User mUserInfoFromServer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); 
		StrictMode.setThreadPolicy(policy);		

		mServerConnectionManager = ServerConnectionManager.getInstance();
		
		mTitleText = (TextView)findViewById(R.id.welcome_activity_title);
		mTitleText.setText(getString(R.string.welcome_activity_want_to_play) + " " + UserManager.getInstance().getUser().getUsername());

		mTextCurrentScore = (TextView)findViewById(R.id.welcome_activity_score);
		
		mPlayButton = (Button) findViewById(R.id.welcome_activity_button); 
		mPlayButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent triviaActivity = new Intent(getApplicationContext(), TriviaActivity.class);
				startActivityForResult(triviaActivity, TriviaActivity.GAME_RESULT);
				
			}
		});
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		mPointsRetrievingTask = new UserPointsRetrievingTask();
		mPointsRetrievingTask.execute((Void) null);
	}
	
	@Override
	protected void onResume() {
		super.onResume();

		mPointsRetrievingTask = new UserPointsRetrievingTask();
		mPointsRetrievingTask.execute((Void) null);
		
	};
	
	
	private void showProgress(boolean b) {
		// TODO show progress
		
	}
	
	public class UserPointsRetrievingTask extends AsyncTask<Void, Void, Boolean> {

		UserPointsRetrievingTask() {
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			Boolean success = false;

			try {
				mUserInfoFromServer = mServerConnectionManager.findCurrentUser();
				success = true;
			} catch (Exception e1) {
				e1.printStackTrace();
				Log.e(TAG, "points info couldnt be retrieved");
			}

			return success;
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			mPointsRetrievingTask = null;
			showProgress(false);

			if (success) {
				mTextCurrentScore.setText(getString(R.string.welcome_activity_current_score) + " " + Long.toString(mUserInfoFromServer.getPoints()));
			} else {
				mTextCurrentScore.setText(getString(R.string.welcome_activity_current_score) + "/n" + getString(R.string.welcome_activity_current_score_not_found));
				Toast t = Toast.makeText(getApplicationContext(), 
						getString(R.string.error_retrofit) + " " +
						mServerConnectionManager.getErrorKind().toString(), 
						Toast.LENGTH_LONG);
				t.show();
			}
		}


		@Override
		protected void onCancelled() {
			mPointsRetrievingTask = null;
			showProgress(false);
		}
	}
	
}
