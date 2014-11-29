package org.coursera.capstone.mutibo.fausto85.client.tests;

import org.coursera.capstone.mutibo.fausto85.R;
import org.coursera.capstone.mutibo.fausto85.client.ui.LoginActivity;
import org.coursera.capstone.mutibo.fausto85.client.ui.WelcomeActivity;

import android.app.Instrumentation.ActivityMonitor;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
//import android.test.ActivityUnitTestCase;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivityTestLogin extends ActivityInstrumentationTestCase2<LoginActivity> {

    private LoginActivity mLoginActivity;
    private AutoCompleteTextView mEmailTextView;
    private EditText mPasswordTextView; 
	private Button mLoginButton;
	
	private final String defaultUser = "coursera";
	private final String defaultPassword = "changeit";
	private final int TIMEOUT_IN_MS = 5000;
	
	public LoginActivityTestLogin() {
		super(LoginActivity.class);

		
		ActivityMonitor receiverActivityMonitor =
		        getInstrumentation().addMonitor(WelcomeActivity.class.getName(),
		        null, false);

		getInstrumentation().runOnMainSync(new Runnable() {
		    @Override
		    public void run() {
		        mEmailTextView.requestFocus();
		    }
		});
		getInstrumentation().waitForIdleSync();
		getInstrumentation().sendStringSync(defaultUser);
		getInstrumentation().waitForIdleSync();

		getInstrumentation().runOnMainSync(new Runnable() {
		    @Override
		    public void run() {
		        mPasswordTextView.requestFocus();
		    }
		});
		getInstrumentation().waitForIdleSync();
		getInstrumentation().sendStringSync(defaultPassword);
		getInstrumentation().waitForIdleSync();

		mLoginButton.performClick();

		WelcomeActivity receiverActivity = (WelcomeActivity) 
		        receiverActivityMonitor.waitForActivityWithTimeout(TIMEOUT_IN_MS);

		assertNotNull("ReceiverActivity is null", receiverActivity);
		assertEquals("Monitor for ReceiverActivity has not been called",
		        1, receiverActivityMonitor.getHits());
		assertEquals("Activity is of wrong type",
		        WelcomeActivity.class, receiverActivity.getClass());

		getInstrumentation().removeMonitor(receiverActivityMonitor);		
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		Intent mLaunchIntent = new Intent(getInstrumentation()
                .getTargetContext(), LoginActivity.class);
        
		mLoginActivity = getActivity();
		mEmailTextView = (AutoCompleteTextView) mLoginActivity.findViewById(R.id.email);
		mPasswordTextView = (EditText) mLoginActivity.findViewById(R.id.password);
		mLoginButton = (Button) mLoginButton.findViewById(R.id.email_sign_in_button);
		
	}
	
}
