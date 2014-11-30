package org.coursera.capstone.mutibo.fausto85.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.annotation.PostConstruct;

import org.coursera.capstone.mutibo.fausto85.server.repo.Trivia;
import org.coursera.capstone.mutibo.fausto85.server.repo.TriviaRepository;
import org.coursera.capstone.mutibo.fausto85.server.repo.User;
import org.coursera.capstone.mutibo.fausto85.server.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;


@Component
public class DBPopulation {

	//private final static String triviaResource = "private/Trivias.json";
	private final static String triviaResource = "private/Trivias0.json";
	
	@Autowired
	private TriviaRepository triviaRepository;
	
	@Autowired
	private UserRepository userRepository;

	@PostConstruct
	public void populateTriviaDB() throws IOException{
		String triviasJson = "";
		Trivia[] trivias = {};
		try {
			Resource res = new ClassPathResource(triviaResource);
			BufferedReader br = new BufferedReader(new InputStreamReader(res.getInputStream()),1024);
			StringBuilder stringBuilder = new StringBuilder();
			String line;
			while ((line = br.readLine()) != null) {
				stringBuilder.append(line).append('\n');
			}
			br.close();
			triviasJson = stringBuilder.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Gson gson = new Gson();
		try{
			trivias = gson.fromJson(triviasJson, Trivia[].class); 
		}catch(Exception e){
			e.printStackTrace();
		}
		 
		try{
			long i = 1;
			 for(Trivia t: trivias){
				 t.setId(i);
				 triviaRepository.save(t);
				 i++;
			 }
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@PostConstruct
	public void populateUserDB() throws IOException{
		long i = 1;
		for(User u : UserTestDatabase.users){
			u.setId(i);
			userRepository.save(u);
			i++;
		}
	}

}
