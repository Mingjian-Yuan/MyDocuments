package de.uniba.wiai.dsg.ajp.assignment3;

import java.util.LinkedList;
import java.util.List;
/**Customer class represents the customer of the store*/
public class Customer {

	private String name;

	private List<Rental> rentals = new LinkedList<Rental>();
	/**constructor , create a Customer object
	 *
	 * @param name the name of customer. must not be null or empty
	 * */
	public Customer(String name) {
		super();
		if(name == null || name.isEmpty()){
			throw new IllegalArgumentException("name cannot be null or empty");
		}
		this.name = name;

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if(name == null || name.isEmpty()){throw new IllegalArgumentException("name cannot be null or empty");}
		this.name = name;
	}

	public List<Rental> getRentals() {
		return rentals;
	}

	public void setRentals(List<Rental> rentals) {
		if(rentals.isEmpty()){throw new IllegalArgumentException("rentals cannot be empty");}
		this.rentals = rentals;
	}



	/**product the complete bill of a customer.
	 * <p>iterating the rentals list of the customer,get the title,the type,the charge and the vat of every movie.
	 * store all of this and the frequent renter points that the customer has been got in the variable "result"</p>
	 *
	 * <p><ul><li>the list of rentals must not be empty</li></ul></p>
	 *
	 * @return the complete bill of a customer as String.
	 * */
	public String statement() {
		String result = "Rental Record for " + getName() + "\n";

		for (Rental each : this.rentals) {
			if (each != null && each.getMovie() != null) {
				// show figures for this rental
				result += "\t" + each.getMovie().getTitle() + each.getType() + "\t"
						+ String.valueOf(each.getCharge()) + "\t"
						+ "incl.VAT " + String.valueOf(each.getVat()) + "\n";
			}
		}

		// add footer lines
		result += "Amount owed is " + String.valueOf(getTotalCharge()) + "\n";
		result += "You earned " + String.valueOf(getTotalFrequentRenterPoints())
				+ " frequent renter points";
		return result;
	}


	/**product the complete bill of a customer in html format.
	 *
	 * <p>Iterating the rentals list of the customer,get the title,the type,the charge and the vat of every movie.</n>
	 * Store all of this and the frequent renter points that the customer has been got in the variable "result"</p>
	 *
	 * <p><ul><li>the list of rentals must not be empty</li></ul></p>
	 *
	 * @return the complete bill in html format as String
	 * */
	public String htmlStatement() {
		String result = "<h1>Rentals for <em>" + getName() + "</em></h1>\n<p>";

		for (Rental each : this.rentals) {
			// show figures for each rental
			result += each.getMovie().getTitle() + " " + each.getType() + ": "
					+ String.valueOf(each.getCharge()) + " incl.VAT:"
		            + String.valueOf(each.getVat()) + "<br>\n";
		}

		result += "</p>\n";

		// add footer lines
		result += "<p>You owe <em>" + String.valueOf(getTotalCharge())
				+ "</em></p>\n";
		result += "<p>On this rental you earned <em>"
				+ String.valueOf(getTotalFrequentRenterPoints())
				+ "</em> frequent renter points</p>";
		return result;
	}


	/**get charge of all rentals of a customer.
	 * <p>Iterating the list of rentals of a customer.and calculate the sum of every charge</p>
	 * <p><ul><li>the list of rentals must not be empty</li></ul></p>
	 *
	 * @return the charge as a double number
	 * */
	double getTotalCharge() {
		double result = 0;

		for (Rental each : rentals) {
			result += each.getCharge();
		}

		return result;
	}

	/**get total points of a customer.
	 *<p>iterating the list of rentals of a customer.and calculate the sum of every frequent renter points</p>
	 *
	 * @return the sum of points as an int number
	 * */
	int getTotalFrequentRenterPoints() {
		int result = 0;

		for (Rental each : rentals) {
			result += each.getFrequentRenterPoints();
		}

		return result;
	}

}
