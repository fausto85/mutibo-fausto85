package org.coursera.capstone.mutibo.fausto85.server.repo;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.google.common.base.Objects;
import com.google.gson.annotations.Expose;

@Entity
public class Trivia {
	
	@Id
	@Expose
	private long id;

	private String movie1;
	private String movie2;
	private String movie3;
	private String movie4;
	private long oddMovie;
	private String explanation;
	private long rates;
	private long level;
	private String oddMovieString;
	private String type;
	
	public Trivia(){
		
	}
	
	public Trivia(String m1, String m2, String m3, String m4, long odd, String exp, 
					long rates, long level, String oddStr, String type, int id){
		this.movie1 = m1;
		this.movie2 = m2;
		this.movie3 = m3;
		this.movie4 = m4;
		this.oddMovie = odd;
		this.explanation = exp;
		this.rates = rates;
		this.level = level;
		this.oddMovieString = oddStr;
		this.type = type;
		this.id = id;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Trivia) {
			Trivia other = (Trivia) obj;
			return Objects.equal(movie1, other.movie1)
					&& Objects.equal(movie2, other.movie2)
					&& Objects.equal(movie3, other.movie3)
					&& Objects.equal(movie4, other.movie4);
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(movie1, movie2, movie3, movie4);
	}
	
	public String getMovie1() {
		return movie1;
	}

	public void setMovie1(String movie1) {
		this.movie1 = movie1;
	}

	public String getMovie2() {
		return movie2;
	}

	public void setMovie2(String movie2) {
		this.movie2 = movie2;
	}

	public String getMovie3() {
		return movie3;
	}

	public void setMovie3(String movie3) {
		this.movie3 = movie3;
	}

	public String getMovie4() {
		return movie4;
	}

	public void setMovie4(String movie4) {
		this.movie4 = movie4;
	}

	public long getOddMovie() {
		return oddMovie;
	}

	public void setOddMovie(long oddMovie) {
		this.oddMovie = oddMovie;
	}

	public String getExplanation() {
		return explanation;
	}

	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}

	public long getRates() {
		return rates;
	}

	public void setRates(long rates) {
		this.rates = rates;
	}

	public long getLevel() {
		return level;
	}

	public void setLevel(long level) {
		this.level = level;
	}

	public String getOddMovieString() {
		return oddMovieString;
	}

	public void setOddMovieString(String oddMovieString) {
		this.oddMovieString = oddMovieString;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
