package org.coursera.capstone.mutibo.fausto85.server.test;

import static org.junit.Assert.*;

import java.util.Collection;

import org.apache.http.HttpStatus;
import org.coursera.capstone.mutibo.fausto85.client.MutiboInterface;
import org.coursera.capstone.mutibo.fausto85.server.repo.Trivia;
import org.coursera.capstone.mutibo.fausto85.server.test.data.TestData;
import org.junit.Test;

import retrofit.ErrorHandler;
import retrofit.RestAdapter;
import retrofit.RestAdapter.LogLevel;
import retrofit.RetrofitError;
import retrofit.client.ApacheClient;

/**
 * 
 * This integration test sends a POST request to the Servlet to add a new
 * Trivia and then sends a second GET request to check that the trivia showed up
 * in the list of trivias. Actual network communication using HTTP is performed
 * with this test.
 * 
 * The test requires that the TriviaSvc be running first (see the directions in
 * the README.md file for how to launch the Application).
 * 
 * To run this test, right-click on it in Eclipse and select
 * "Run As"->"JUnit Test"
 * 
 * Pay attention to how this test that actually uses HTTP and the test that just
 * directly makes method calls on a TriviaSvc object are essentially identical.
 * All that changes is the setup of the TriviaService variable. Yes, this could
 * be refactored to eliminate code duplication...but the goal was to show how
 * much Retrofit simplifies interaction with our service!
 * 
 * @author jules
 *
 */
public class MutiboClientApiTest {

	private class ErrorRecorder implements ErrorHandler {

		private RetrofitError error;

		@Override
		public Throwable handleError(RetrofitError cause) {
			error = cause;
			return error.getCause();
		}

		public RetrofitError getError() {
			return error;
		}
	}

	private final String TEST_URL = "https://localhost:8443";

	private MutiboInterface mutiboService = new RestAdapter.Builder()
			.setClient(new ApacheClient(UnsafeHttpsClient.createUnsafeClient()))
			.setEndpoint(TEST_URL).setLogLevel(LogLevel.FULL).build()
			.create(MutiboInterface.class);

	private Trivia randomTrivia = TestData.randomTrivia();

	/**
	 * This test creates a Trivia and attempts to add it to the Trivia service
	 * without logging in. The test checks to make sure that the request is
	 * denied and the client redirected to the login page.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testRedirectToLoginWithoutAuth() throws Exception {
		ErrorRecorder error = new ErrorRecorder();

		MutiboInterface mutiboService = new RestAdapter.Builder()
				.setClient(
						new ApacheClient(UnsafeHttpsClient.createUnsafeClient()))
				.setEndpoint(TEST_URL).setLogLevel(LogLevel.FULL)
				.setErrorHandler(error).build().create(MutiboInterface.class);
		try {
			// This should fail because we haven't logged in!
			mutiboService.addTrivia(randomTrivia);

			fail("Yikes, the security setup is horribly broken and didn't require the user to login!!");

		} catch (Exception e) {
			// Ok, our security may have worked, ensure that
			// we got redirected to the login page
			assertEquals(HttpStatus.SC_MOVED_TEMPORARILY, error.getError()
					.getResponse().getStatus());
		}
	}

	/**
	 * This test creates a Trivia and attempts to add it to the Trivia service
	 * without logging in. The test checks to make sure that the request is
	 * denied and the client redirected to the login page.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testDenyTriviaAddWithoutLogin() throws Exception {
		ErrorRecorder error = new ErrorRecorder();

		MutiboInterface mutiboService = new RestAdapter.Builder()
				.setClient(
						new ApacheClient(UnsafeHttpsClient.createUnsafeClient()))
				.setEndpoint(TEST_URL).setLogLevel(LogLevel.FULL)
				.setErrorHandler(error).build().create(MutiboInterface.class);
		try {

			// This should fail because we haven't logged in!
			mutiboService.addTrivia(randomTrivia);

			fail("Yikes, the security setup is horribly broken and didn't require the user to login!!");

		} catch (Exception e) {
			// Ok, our security may have worked, ensure that
			// we got redirected to the login page
			assertEquals(HttpStatus.SC_MOVED_TEMPORARILY, error.getError()
					.getResponse().getStatus());
		}

		
		// Now, let's login and ensure that the Trivia wasn't added
		mutiboService.login("coursera", "changeit");

		// We should NOT get back the Trivia that we added above!
		Collection<Trivia> set = mutiboService.getTriviaList();
		assertFalse(set.contains(set));
	}

	/**
	 * This test creates a Trivia, adds the Trivia to the TriviaSvc, and then
	 * checks that the Trivia is included in the list when getTriviaList() is
	 * called.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testTriviaAddAndList() throws Exception {

		mutiboService.login("coursera", "changeit");

		// Add the Trivia
		mutiboService.addTrivia(randomTrivia);

		// We should get back the Trivia that we added above
		Collection<Trivia> trivias = mutiboService.getTriviaList();
		assertTrue(trivias.contains(randomTrivia));
	}
	
	/**
	 * This test creates a Trivia, adds the Trivia to the TriviaSvc, and then
	 * checks that the Trivia is included in the list when getTriviaList() is
	 * called.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testLogout() throws Exception {

		mutiboService.login("coursera", "changeit");

		// Add the Trivia, which should succeed
		mutiboService.addTrivia(randomTrivia);

		mutiboService.logout();
		
		try{
			mutiboService.getTriviaList();
			fail("We shouldn't make it here if logout works!");
		}catch(Exception e){
			//OK, logout worked
		}

	}

	@Test
	public void testAutoloadingTrivias() throws Exception {
		ErrorRecorder error = new ErrorRecorder();

		MutiboInterface mutiboService = new RestAdapter.Builder()
				.setClient(
						new ApacheClient(UnsafeHttpsClient.createUnsafeClient()))
				.setEndpoint(TEST_URL).setLogLevel(LogLevel.FULL)
				.setErrorHandler(error).build().create(MutiboInterface.class);
		try {
			mutiboService.login("coursera", "changeit");
			Collection<Trivia> trivias = mutiboService.getTriviaList();

			Trivia expectedTrivia = new Trivia("American Pie", "Mean Girls", "Titanic", "The Breakfast Club",
				    3,"By Genre: Titanic is not a High school movie",0, 0,"Titanic", "Genre");

			assertTrue(trivias.contains(expectedTrivia));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
