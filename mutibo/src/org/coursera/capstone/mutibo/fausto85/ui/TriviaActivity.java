package org.coursera.capstone.mutibo.fausto85.ui;

import org.coursera.capstone.mutibo.fausto85.R;
import org.coursera.capstone.mutibo.fausto85.R.id;
import org.coursera.capstone.mutibo.fausto85.R.layout;
import org.coursera.capstone.mutibo.fausto85.R.menu;
import org.coursera.capstone.mutibo.fausto85.question.Set;
import org.coursera.capstone.mutibo.fausto85.question.TestQuestions;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.os.Build;

public class TriviaActivity extends Activity {

	private static final String TAG = "TriviaActity";
	public static final int GAME_RESULT = 1;
	private static final Boolean TEST_OFFLINE = true;  
	private static int mQuestionIndex = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trivia);
		mQuestionIndex = 0;
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}
		
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.trivia, menu);
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		private Button mSubmitButton;
		private RadioGroup mRadioGroup;

		public PlaceholderFragment() {
			if(TEST_OFFLINE){
				TestQuestions.init();
			}else{
				showError();
			}
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_trivia,
					container, false);

			mSubmitButton = (Button)rootView.findViewById(R.id.trivia_submit_button);
			mRadioGroup = (RadioGroup)rootView.findViewById(R.id.radioGroup1);

			showQuestion(mQuestionIndex);
			
			mSubmitButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					mQuestionIndex++;
					if(mQuestionIndex < TestQuestions.TEST_NUMBER_OF_QUESTIONS){
						getFragmentManager().beginTransaction().replace(
								R.id.container, new PlaceholderFragment()).commit();
					}else{
						getActivity().finish();
					}
						
				}
			});
			
			return rootView;
		}

		private void showQuestion(int i) {
			Set question = TestQuestions.questions[i];
			Set.Option[] options = question.getOptions();
			for(int j = 0; j< mRadioGroup.getChildCount(); j++){
				RadioButton rb = (RadioButton)mRadioGroup.getChildAt(j);
				rb.setText(options[j].getText());
			}
		}

		private void showError() {
			// TODO Auto-generated method stub
			
		}
	
	}
}
