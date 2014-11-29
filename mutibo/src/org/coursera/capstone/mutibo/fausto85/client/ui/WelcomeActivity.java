package org.coursera.capstone.mutibo.fausto85.client.ui;

import org.coursera.capstone.mutibo.fausto85.R;
import org.coursera.capstone.mutibo.fausto85.client.AuthenticationInfo;
import org.coursera.capstone.mutibo.fausto85.client.AuthenticationManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class WelcomeActivity extends Activity {

	private static final String TAG = "WelcomeActity";
	private TextView mTitleText;
	private Button mPlayButton;
	private static AuthenticationManager mAccountManager;
	private static AuthenticationInfo mAccount;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		mAccountManager = AuthenticationManager.getInstance();
		mAccount = mAccountManager.getCurrentAccount();
		
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
	
	
}
