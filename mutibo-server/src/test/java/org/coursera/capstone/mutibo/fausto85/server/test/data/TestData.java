package org.coursera.capstone.mutibo.fausto85.server.test.data;

import org.coursera.capstone.mutibo.fausto85.server.repo.Trivia;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This is a utility class to aid in the construction of
 * Trivia objects with random names, urls, and durations.
 * 
 * @author jules
 *
 */
public class TestData {

	private static final ObjectMapper objectMapper = new ObjectMapper();
	
	/**
	 * Construct and return a Trivia object with a
	 * rnadom name, url, and duration.
	 * 
	 * @return
	 */
	public static Trivia randomTrivia() {
		return new Trivia("A","B","C","D",2, "exp", 0,0,"odd string", "type", -1);
	}
	
	/**
	 *  Convert an object to JSON using Jackson's ObjectMapper
	 *  
	 * @param o
	 * @return
	 * @throws Exception
	 */
	public static String toJson(Object o) throws Exception{
		return objectMapper.writeValueAsString(o);
	}
}
