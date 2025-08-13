package de.uniba.wiai.dsg.ajp.assignment3;
/**Rental class represents a customer renting a movie*/
public class Rental {

	private int daysRented;
	private Movie movie;

	public static final String streaming = "Streaming";
	public static final String dvd = "DVD";

	private final String type;


	public Rental(String type, int daysRented){
		if(daysRented<1){throw new IllegalArgumentException("days rented must be >=1");}
		if(type == null || type.isEmpty() || (!type.equals(dvd) && !type.equals(streaming))) {
			throw new IllegalArgumentException("type invalid");
		}
		this.type = type;
		if(type.equals(streaming)){
		this.daysRented = 1;}
		else {this.daysRented=daysRented;}
	}

	public String getType(){
		return type;
	}


	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public Movie getMovie() {
		return movie;
	}

	public int getDaysRented() {
		return daysRented;
	}


	public void setDaysRented(int daysRented) {
		if(daysRented<1){throw new IllegalArgumentException("days rented must be >=1");}
		if (type.equals(streaming)) {
			throw new IllegalArgumentException("Cannot set days rented for streaming movies");
		}
		this.daysRented = daysRented;
	}


	public double getCharge() {
		return movie.getCharge(daysRented);
	}


	public double getVat(){ return movie.getVat(daysRented);}

	public int getFrequentRenterPoints() {
		return movie.getFrequentRenterPoints(daysRented);
	}

}
