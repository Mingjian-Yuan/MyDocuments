package de.uniba.wiai.dsg.ajp.assignment3;
/** data class of movies*/
public class Movie {
	/**inner class of Movie.
	 * <p>3 types of movies,also the difference pricing methods</p>
	 */
	public enum priceCode{
		REGULAR,
		NEW_RELEASE,
		CHILDREN
	}
	/**the 3 static values of enum class priceCode*/
	public static final int REGULAR = 0;
	public static final int NEW_RELEASE = 1;
	public static final int CHILDREN = 2;
	/**the 2 static values of imageQuality*/
	public static final String HD = "HD";
	public static final String fourK = "4K";

	private Price price;

	private String title;
	private final String imageQuality;


	/** constructor of class Movie
	 *
	 * @param title the title of Movie, must not be null or empty.
	 * @param priceCode one of three constants in enum class priceCode .
	 * @param imageQuality the quality of movie, must not be null or empty,and only has 4K or HD two choices.
	 */
	public Movie(String title, priceCode priceCode, String imageQuality) {
		if(title == null ||title.isEmpty()){throw new IllegalArgumentException("title cannot be null or empty");}
		if(imageQuality == null || imageQuality.isEmpty() || (!imageQuality.equals(HD) && !imageQuality.equals(fourK))){
			throw new IllegalArgumentException("image quality invalid");
		}
		this.title = title;
		this.setPriceCode(priceCode);
		this.imageQuality = imageQuality;
	}

	public String getTitle() {
		return title + " (" + imageQuality + ")";
	}


	/**set the title of the movie
	 *
	 * @param title the name of the movie. must not be null or empty.
	 * */
	public void setTitle(String title) {
		if(title == null||title.isEmpty()){throw new IllegalArgumentException("title cannot be null or empty");}
		this.title = title;
	}


	/**get the charge of a rental with vat.
	 * <p>based on the number of days rented.
	 * And if the image Quality of the movie is 4K ,it costs 2 euros more</p>
	 *
	 * @param daysRented the number of days rented, must not be < 1 .
	 * @return the charge of a rental.
	 */
	double getCharge(int daysRented) {
		if(daysRented<1){throw new IllegalArgumentException("days rented must be >= 1");}
		double charge = price.getCharge(daysRented);
		if (imageQuality.equals(fourK)) {
			charge += 2.0;
		}
		return charge;
	}


	/** get the Vat of a rental
	 * <p>based on the charge and the vat rate that has been set</p>
	 *
	 * @param daysRented the number of days rented , must not be <1.
	 * @return the Vat of a rental
	 * */
	double getVat(int daysRented){
		if(daysRented<1){throw new IllegalArgumentException("days rented must be >=1");}
		price.setVatRate(0.19);
		double vatRate = price.getVatRate();
		double vat = price.getCharge(daysRented)*vatRate;
		vat = Math.round(vat * 10.0) / 10.0;
		return vat;
	}

	public int getPriceCode() {
		return price.getPriceCode();
	}

	/**set the price-code of the movie based on the type of the movie.
	 *
	 * @param priceCode one of three constants in enum class priceCode.
	 * */
	public void setPriceCode(priceCode priceCode) {
		switch (priceCode) {
		case REGULAR:
			price = new RegularPrice();
			break;
		case CHILDREN:
			price = new ChildrenPrice();
			break;
		case NEW_RELEASE:
			price = new NewReleasePrice();
			break;
		default:
			throw new IllegalArgumentException("Incorrect Price Code");
		}
	}


	/**get the points earned of a rental
	 *
	 * @param daysRented the number of days rented . must not be <1 .
	 * @return the points earned of a rental
	 * */
	public int getFrequentRenterPoints(int daysRented) {
		if(daysRented<1){throw new IllegalArgumentException("days rented must be >=1");}
		return price.getFrequentRenterPoints(daysRented);
	}

}
