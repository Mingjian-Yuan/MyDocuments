package de.uniba.wiai.dsg.ajp.assignment3;

public class NewReleasePrice extends Price{

	@Override
	double getCharge(int daysRented) {
		if(daysRented<1){throw new IllegalArgumentException("days rented must be >=1");}
		return daysRented * 3;
	}

	@Override
	int getFrequentRenterPoints(int daysRented) {
		if(daysRented<1){throw new IllegalArgumentException("days rented must be >=1");}
		if(daysRented > 1) {
			return 2;
		} else {
			return 1;
		}
	}

	@Override
	int getPriceCode() {
		return Movie.NEW_RELEASE;
	}
	
}
