package org.coursera.capstone.mutibo.fausto85.client.ui;

import org.coursera.capstone.mutibo.fausto85.R;
import org.coursera.capstone.mutibo.fausto85.client.connection.TriviaUpdate;
import org.coursera.capstone.mutibo.fausto85.client.question.*;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class TriviaActivity extends Activity {

	private static final String TAG = "TriviaActity";
	public static final int GAME_RESULT = 1;

	private QuestionManager mQuestionManager;
	private TriviaLoadingTask mTriviaLoadingTask = null;
	private AnswerManager mAnswerManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_trivia);
		mQuestionManager = QuestionManager.getInstance();
		mAnswerManager = AnswerManager.getInstance();
		mAnswerManager.initPlaySession();
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
		//TODO: Show animation
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
		Toast t = Toast.makeText(getApplicationContext(), getString(R.string.trivia_activity_server_error), Toast.LENGTH_LONG);
		t.show();
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
		private AnswerManager mAnswerManager;
		private Trivia mCurrentTrivia;
		private Answer mAnswer;
		private int mTries;
		private View mRootView; 
		private UserSyncTask mUserSyncTask;

		public TriviaQuestionFragment() {
			mQuestionManager = QuestionManager.getInstance();
			mAnswerManager = AnswerManager.getInstance();
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
								R.id.container, new TriviaAnswerFragment(mCurrentTrivia, mAnswer)).commit();
						Log.d(TAG, "show next trivia");
					}else if(mTries == Answer.MAX_TRIES){
						mAnswer.setPoints(0);
						getFragmentManager().beginTransaction().replace(
								R.id.container, new TriviaAnswerFragment(mCurrentTrivia, mAnswer)).commit();
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
				mUserSyncTask = new UserSyncTask();
				mUserSyncTask.execute((Void) null);

				//gameSessionFinished();
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
			Toast t = Toast.makeText(getActivity().getApplicationContext(), getString(R.string.trivia_fragment_incorrect_answer), Toast.LENGTH_SHORT);
			t.show();
		}
		
		public class UserSyncTask extends AsyncTask<Void, Void, Boolean> {

			UserSyncTask() {
			}

			@Override
			protected Boolean doInBackground(Void... params) {
				Boolean b1 = mAnswerManager.syncAnswers();
				Boolean b2 = mAnswerManager.syncRatings();
				return (b1 && b2);
			}

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				showProgress(true);
			}

			@Override
			protected void onPostExecute(final Boolean success) {
				mUserSyncTask = null;
				showProgress(false);

				if (success) {
					gameSessionFinished();
				} else {
					showError();
				}
			}

			@Override
			protected void onCancelled() {
				mUserSyncTask = null;
				showProgress(false);
			}

			public void showProgress(final boolean show) {
				//TODO: Show progress
			}
			public void showError() {
				Toast t = Toast.makeText(getActivity().getApplicationContext(), getString(R.string.trivia_activity_server_error), Toast.LENGTH_LONG);
				t.show();
				//finish();
			}
		}
		
	}

	
	public static class TriviaAnswerFragment extends Fragment{
		private View mRootView; 
		private Trivia mCurrentTrivia;
		private Answer mAnswer;
		private AnswerManager mAnswerManager;
		private TextView mPointsTextView;
		private TextView mExplanationTextView;
		private ImageButton mLikeButton;
		private ImageButton mDislikeButton;
	    
		public TriviaAnswerFragment(Trivia trivia, Answer answer){
			mAnswerManager = AnswerManager.getInstance();
			mCurrentTrivia = trivia;
			mAnswer = answer;
		}
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			mRootView = inflater.inflate(R.layout.fragment_trivia_answer,
					container, false);
			mPointsTextView = (TextView)mRootView.findViewById(R.id.textViewPointsObtained);
			mExplanationTextView = (TextView)mRootView.findViewById(R.id.textViewExplanation);
			mLikeButton = (ImageButton)mRootView.findViewById(R.id.ImageButtonLike);
			mDislikeButton = (ImageButton)mRootView.findViewById(R.id.ImageButtonDisLike);
			
			mPointsTextView.setText(String.valueOf(mAnswer.getPoints()));
			mExplanationTextView.setText(mCurrentTrivia.getExplanation());
			
			mLikeButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					//mAnswerManager.saveTriviaUpdate(mCurrentTrivia.getId(), TriviaUpdate.Rating.LIKE);
					saveAnswerAndShowNextTrivia();
				}
			});
			
			mDislikeButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					mAnswerManager.saveTriviaUpdate(mCurrentTrivia.getId(), TriviaUpdate.Rating.DISLIKE);
					saveAnswerAndShowNextTrivia();
				}
			});

			final GestureDetector gesture = new GestureDetector(getActivity(),
		            new GestureDetector.SimpleOnGestureListener() {

				@Override
				public boolean onDown(MotionEvent e) {
					return true;
				}

				@Override
				public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
						float velocityY) {

					final int SWIPE_MIN_DISTANCE = 120;
					final int SWIPE_MAX_OFF_PATH = 250;
					final int SWIPE_THRESHOLD_VELOCITY = 200;
					try {
						if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
							return false;
						if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
								&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
							Log.i(TAG, "Right to Left");
							saveAnswerAndShowNextTrivia();
						} else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
								&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
							Log.i(TAG, "Left to Right");
						}
					} catch (Exception e) {
						// nothing
					}
					return super.onFling(e1, e2, velocityX, velocityY);
				}
			});

			mRootView.setOnTouchListener(new View.OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					return gesture.onTouchEvent(event);
				}
			});

			return mRootView;
		}
		
		private void saveAnswerAndShowNextTrivia() {
			mAnswerManager.saveAnswer(mAnswer);
			getFragmentManager().beginTransaction().replace(
					R.id.container, new TriviaQuestionFragment()).commit();
		}
		
		
	}
}
