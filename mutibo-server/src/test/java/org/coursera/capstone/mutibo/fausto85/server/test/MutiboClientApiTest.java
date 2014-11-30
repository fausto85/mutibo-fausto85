package org.coursera.capstone.mutibo.fausto85.server.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpStatus;
import org.coursera.capstone.mutibo.fausto85.client.MutiboInterface;
import org.coursera.capstone.mutibo.fausto85.server.UserTestDatabase;
import org.coursera.capstone.mutibo.fausto85.server.repo.Trivia;
import org.coursera.capstone.mutibo.fausto85.server.repo.TriviaUpdate;
import org.coursera.capstone.mutibo.fausto85.server.repo.User;
import org.coursera.capstone.mutibo.fausto85.server.repo.TriviaUpdate.Rating;
import org.coursera.capstone.mutibo.fausto85.server.test.data.TestData;
import org.junit.Before;
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
	
	@Before
	public void setUp(){
		UserTestDatabase.init();
	}
	
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
		mutiboService.login(UserTestDatabase.users.get(0).getUsername(), UserTestDatabase.passwords.get(0));

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

		mutiboService.login(UserTestDatabase.users.get(0).getUsername(), UserTestDatabase.passwords.get(0));

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

		mutiboService.login(UserTestDatabase.users.get(5).getUsername(), UserTestDatabase.passwords.get(5));

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
			mutiboService.login(UserTestDatabase.users.get(0).getUsername(), UserTestDatabase.passwords.get(0));
			Collection<Trivia> trivias = mutiboService.getTriviaList();

			Trivia expectedTrivia = new Trivia("American Pie", "Mean Girls", "Titanic", "The Breakfast Club",
				    3,"By Genre: Titanic is not a High school movie",0, 0,"Titanic", "Genre", 0);

			assertTrue(trivias.contains(expectedTrivia));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testFindUser() throws Exception {
		ErrorRecorder error = new ErrorRecorder();

		MutiboInterface mutiboService = new RestAdapter.Builder()
				.setClient(
						new ApacheClient(UnsafeHttpsClient.createUnsafeClient()))
				.setEndpoint(TEST_URL).setLogLevel(LogLevel.FULL)
				.setErrorHandler(error).build().create(MutiboInterface.class);
		try {
			mutiboService.login(UserTestDatabase.users.get(0).getUsername(), UserTestDatabase.passwords.get(0));
			
			Collection<User> users = mutiboService.findByUsername("user1");

			assertTrue(users.contains(new User("user1")));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testUserPointsAreUpdated() throws Exception {
		ErrorRecorder error = new ErrorRecorder();

		MutiboInterface mutiboService = new RestAdapter.Builder()
				.setClient(
						new ApacheClient(UnsafeHttpsClient.createUnsafeClient()))
				.setEndpoint(TEST_URL).setLogLevel(LogLevel.FULL)
				.setErrorHandler(error).build().create(MutiboInterface.class);
		try {
			mutiboService.login(UserTestDatabase.users.get(0).getUsername(), UserTestDatabase.passwords.get(0));
			User u = new User("user1", 5);
			mutiboService.updateUser(u);
			mutiboService.updateUser(new User("user1", 10));
			
			Collection<User> usersFromRepo = mutiboService.findByUsername("user1");

			assertTrue(usersFromRepo.iterator().next().getPoints() == 15);

		} catch (Exception e) {
			e.printStackTrace();
			fail("");
		}
	}

	@Test
	@Deprecated
	public void testBadRatingForTrivia() throws Exception {
		ErrorRecorder error = new ErrorRecorder();

		MutiboInterface mutiboService = new RestAdapter.Builder()
				.setClient(
						new ApacheClient(UnsafeHttpsClient.createUnsafeClient()))
				.setEndpoint(TEST_URL).setLogLevel(LogLevel.FULL)
				.setErrorHandler(error).build().create(MutiboInterface.class);
		try {
			mutiboService.login(UserTestDatabase.users.get(0).getUsername(), UserTestDatabase.passwords.get(0));
			
			Collection<Trivia> trivias = mutiboService.getTriviaList();
			Iterator<Trivia> iterator = trivias.iterator();
			Trivia triviaBeforeUpdate = iterator.next();
			List <TriviaUpdate> updates = new ArrayList<TriviaUpdate>();
			
			//update trivia 0
			updates.add(new TriviaUpdate(triviaBeforeUpdate.getId(), Rating.DISLIKE, 0));

			//update trivia 4
			iterator.next();
			iterator.next();
			iterator.next();
			triviaBeforeUpdate = iterator.next();
			
			updates.add(new TriviaUpdate(triviaBeforeUpdate.getId(), Rating.DISLIKE, 0));
			
			mutiboService.updateTrivia(updates);
			
			Collection<Trivia> triviasAfterUpdate = mutiboService.getTriviaList();
			iterator = triviasAfterUpdate.iterator();

			//assert trivia 0 was updated
			Trivia triviaAfterUpdate = iterator.next();
			assertTrue(triviaAfterUpdate.getRates() == -1);

			//assert trivia 1 was not updated
			triviaAfterUpdate = iterator.next();
			assertTrue(triviaAfterUpdate.getRates() == 0);

			//assert trivia 4 was updated
			iterator.next();
			iterator.next();
			triviaAfterUpdate = iterator.next();
			assertTrue(triviaAfterUpdate.getRates() == -1);

		} catch (Exception e) {
			e.printStackTrace();
			fail("");
		}
	}

	
	@Test
	public void testBadRatingsDeleteTrivia() throws Exception {
		ErrorRecorder error = new ErrorRecorder();

		MutiboInterface mutiboService = new RestAdapter.Builder()
				.setClient(
						new ApacheClient(UnsafeHttpsClient.createUnsafeClient()))
				.setEndpoint(TEST_URL).setLogLevel(LogLevel.FULL)
				.setErrorHandler(error).build().create(MutiboInterface.class);
		try {
			mutiboService.login(UserTestDatabase.users.get(0).getUsername(), UserTestDatabase.passwords.get(0));
			
			Collection<Trivia> trivias = mutiboService.getTriviaList();
			Iterator<Trivia> iterator = trivias.iterator();
			Trivia triviaBeforeUpdate = iterator.next();
			triviaBeforeUpdate = iterator.next();
			List <TriviaUpdate> updates = new ArrayList<TriviaUpdate>();
			
			//update trivia 1
			updates.add(new TriviaUpdate(triviaBeforeUpdate.getId(), Rating.DISLIKE, 0));

			//dislike 3 times (should be enough because it's the >10 % of the users (20 -> 2)
			mutiboService.updateTrivia(updates);
			mutiboService.updateTrivia(updates);
			mutiboService.updateTrivia(updates);
			
			Collection<Trivia> triviasAfterUpdate = mutiboService.getTriviaList();
			
			for(Trivia t : triviasAfterUpdate){
				if(t.getId() == 1)
					fail("the trivia with id == 1 was NOT deleted");
			}

		} catch (Exception e) {
			e.printStackTrace();
			fail("");
		}
	}
	
}
