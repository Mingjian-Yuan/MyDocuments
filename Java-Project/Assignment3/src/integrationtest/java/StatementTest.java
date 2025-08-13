import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import de.uniba.wiai.dsg.ajp.assignment3.*;

public class StatementTest {
    private Customer customer;
    private Movie movie1;
    private Movie movie2;

    @BeforeEach
    public void setUp() {
        customer = new Customer("Lily");

        movie1 = new Movie("Movie 1", Movie.priceCode.REGULAR,"HD");
        movie2 = new Movie("Movie 2", Movie.priceCode.NEW_RELEASE,"4K");

        Rental rental1 = new Rental("DVD",3);
        rental1.setMovie(movie1);


        Rental rental2 = new Rental("Streaming",1);
        rental2.setMovie(movie2);


        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);

        customer.setRentals(rentals);
    }

    @Test
    public void testStatement() {
        String expectedStatement = "Rental Record for Lily\n" +
                "\tMovie 1 (HD)DVD\t3.5\tincl.VAT 0.7\n" +
                "\tMovie 2 (4K)Streaming\t5.0\tincl.VAT 0.6\n" +
                "Amount owed is 8.5\n" +
                "You earned 2 frequent renter points";
        String actualStatement = customer.statement();
        Assertions.assertEquals(expectedStatement, actualStatement);
    }

    @Test
    public void testHtmlStatement() {
        String expectedHtmlStatement = "<h1>Rentals for <em>"+"Lily"+"</em></h1>\n<p>" +
                "Movie 1 (HD) DVD: 3.5 incl.VAT:0.7<br>\n" +
                "Movie 2 (4K) Streaming: 5.0 incl.VAT:0.6<br>\n" +
                "</p>\n" +
                "<p>You owe <em>8.5</em></p>\n" +
                "<p>On this rental you earned <em>"+"2"+"</em> frequent renter points</p>";
        String actualHtmlStatement = customer.htmlStatement();
        Assertions.assertEquals(expectedHtmlStatement, actualHtmlStatement);
    }
}
