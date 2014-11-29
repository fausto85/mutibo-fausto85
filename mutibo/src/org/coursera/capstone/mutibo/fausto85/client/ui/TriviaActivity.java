package org.coursera.capstone.mutibo.fausto85.client.ui;

import org.coursera.capstone.mutibo.fausto85.R;
import org.coursera.capstone.mutibo.fausto85.client.question.*;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class TriviaActivity extends Activity {

	private static final String TAG = "TriviaActity";
	public static final int GAME_RESULT = 1;

	private QuestionManager mQuestionManager;
	private TriviaLoadingTask mTriviaLoadingTask = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_trivia);
		mQuestionManager = QuestionManager.getInstance();
		mTriviaLoadingTask = new TriviaLoadingTask();
		mTriviaLoadingTask.execute((Void) null);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
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

	public void showProgress(final boolean show) {
//		int shortAnimTime = getResources().getInteger(
//				android.R.integer.config_shortAnimTime);
//
//		mFormView.setVisibility(show ? View.GONE : View.VISIBLE);
//		mFormView.animate().setDuration(shortAnimTime)
//		.alpha(show ? 0 : 1)
//		.setListener(new AnimatorListenerAdapter() {
//			@Override
//			public void onAnimationEnd(Animator animation) {
//				mFormView.setVisibility(show ? View.GONE
//						: View.VISIBLE);
//			}
//		});
//
//		mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
//		mProgressView.animate().setDuration(shortAnimTime)
//		.alpha(show ? 1 : 0)
//		.setListener(new AnimatorListenerAdapter() {
//			@Override
//			public void onAnimationEnd(Animator animation) {
//				mProgressView.setVisibility(show ? View.VISIBLE
//						: View.GONE);
//			}
//		});
	}
	
	private void showError() {
		// TODO Auto-generated method stub
		finish();
	}

	public class TriviaLoadingTask extends AsyncTask<Void, Void, Boolean> {

		TriviaLoadingTask() {
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			return mQuestionManager.loadQuestions();
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			showProgress(true);
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			mTriviaLoadingTask = null;
			showProgress(false);

			if (success) {
				getFragmentManager().beginTransaction()
				.add(R.id.container, new TriviaQuestionFragment()).commit();
			} else {
				showError();
			}
		}

		@Override
		protected void onCancelled() {
			mTriviaLoadingTask = null;
			showProgress(false);
		}
	}
	
	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class TriviaQuestionFragment extends Fragment {

		private RadioGroup mRadioGroup;
		private QuestionManager mQuestionManager;
		private Trivia mCurrentTrivia;
		private Answer mAnswer;
		private int mTries;
		private View mRootView; 

		public TriviaQuestionFragment() {
			mQuestionManager = QuestionManager.getInstance();
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {

			mRootView = inflater.inflate(R.layout.fragment_trivia_question,
					container, false);
			mRadioGroup = (RadioGroup)mRootView.findViewById(R.id.radioGroup1);

			mRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					String radioButtonSelectedName = getResources().getResourceEntryName(checkedId);
					String radioButtonCorrectAnswerName = "radio" + mCurrentTrivia.getOddMovie();
					Boolean isCorrectOption = false;  

					if(radioButtonSelectedName.equals(radioButtonCorrectAnswerName)) 
						isCorrectOption = true;
					
					if(isCorrectOption && mTries < Answer.MAX_TRIES){
						mAnswer.setPoints(Answer.POINTS_PER_ANSWER[mTries]);
						getFragmentManager().beginTransaction().replace(
								R.id.container, new TriviaQuestionFragment()).commit();
						Log.d(TAG, "show next trivia");
					}else if(mTries == Answer.MAX_TRIES){
						mAnswer.setPoints(0);
						getFragmentManager().beginTransaction().replace(
								R.id.container, new TriviaQuestionFragment()).commit();
						Log.d(TAG, "show next trivia");
					}else{
						showWrongAnswer();
					}

					mTries++;
				}

			});
				
			mCurrentTrivia = mQuestionManager.getNextTrivia();
			if(mCurrentTrivia != null){
				showTrivia();
				mAnswer = new Answer(0, (int)mCurrentTrivia.getId());
				mTries = 0;
			}else{
				gameSessionFinished();
			}

			return mRootView;
		}

		private void gameSessionFinished() {
			getActivity().finish();
		}

		private void showTrivia() {
			RadioButton rb;

			rb = (RadioButton)mRadioGroup.getChildAt(0);
			rb.setText(mCurrentTrivia.getMovie1());

			rb = (RadioButton)mRadioGroup.getChildAt(1);
			rb.setText(mCurrentTrivia.getMovie2());
			
			rb = (RadioButton)mRadioGroup.getChildAt(2);
			rb.setText(mCurrentTrivia.getMovie3());
			
			rb = (RadioButton)mRadioGroup.getChildAt(3);
			rb.setText(mCurrentTrivia.getMovie4());
		}

		private void showWrongAnswer() {
			// TODO Auto-generated method stub
			
		}
	}
	
}
