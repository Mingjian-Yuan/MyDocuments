package de.uniba.wiai.dsg.ajp.assignment3;
/**calculate the price based of rented days*/
public abstract class Price {


	abstract double getCharge(int daysRented);

	int getFrequentRenterPoints(int daysRented) {
		if(daysRented<1){throw new IllegalArgumentException("days rented must be >=1");}
		return 1;
	}


	abstract int getPriceCode();

	private double vatRate;

	public double getVatRate(){
		return vatRate;
	}

	public void setVatRate(double vatRate){
		if(vatRate<0){throw new IllegalArgumentException("vat rate must be >0");}
		this.vatRate = vatRate;
	}

}
