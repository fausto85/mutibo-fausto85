package org.coursera.capstone.mutibo.fausto85.client.tests;

import org.coursera.capstone.mutibo.fausto85.client.ui.TriviaActivity;

import android.test.ActivityInstrumentationTestCase2;

public class TriviaActivityTest extends ActivityInstrumentationTestCase2<TriviaActivity>{

    private TriviaActivity mTriviaActivity;
//    private TextView mTestText;
	
	
	public TriviaActivityTest() {
		super(TriviaActivity.class);
	}
	
	   @Override
	    protected void setUp() throws Exception {
	        super.setUp();
	        mTriviaActivity = getActivity();
//	        mTestText =
//	                (TextView) mTriviaActivity
//	                .findViewById(R.id.);
	    }

}
