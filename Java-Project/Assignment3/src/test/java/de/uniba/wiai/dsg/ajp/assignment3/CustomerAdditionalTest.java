package de.uniba.wiai.dsg.ajp.assignment3;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerAdditionalTest {

    private Customer customer;
    private List<Rental> rentals;

    @BeforeEach
    public void setup() {
        customer = new Customer("Test Customer");
        rentals = new ArrayList<>();
    }

    @Test
    public void testCustomerConstructor_nullName() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            new Customer(null));

        String expectedMessage = "name cannot be null or empty";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testCustomerConstructor_emptyName() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            new Customer(""));

        String expectedMessage = "name cannot be null or empty";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
    @Test
    public void testSetName_nullName() {
        Exception exception = assertThrows(IllegalArgumentException.class,()->
                new Customer(null));

        String expectedMessage = "name cannot be null or empty";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testSetName_emptyName() {
        Exception exception = assertThrows(IllegalArgumentException.class,()->
                new Customer(""));

        String expectedMessage = "name cannot be null or empty";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }


    @Test
    void testSetRentals_emptyName() {
        Customer customer = new Customer("Test Customer");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            customer.setRentals(new ArrayList<>());
        });

        String expectedMessage = "rentals cannot be empty";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }



    @Test
    public void testStatement_emptyRentals() {
        String expectedStatement = "Rental Record for Test Customer\n" +
                "Amount owed is 0.0\n" +
                "You earned 0 frequent renter points";
        String actualStatement = customer.statement();

        assertEquals(expectedStatement, actualStatement);
    }

    @Test
    public void testStatement_multipleRentals() {
        Rental mockRental1 = mock(Rental.class);
        Rental mockRental2 = mock(Rental.class);
        Movie mockMovie1 = mock(Movie.class);
        Movie mockMovie2 = mock(Movie.class);

        when(mockRental1.getMovie()).thenReturn(mockMovie1);
        when(mockRental1.getMovie().getTitle()).thenReturn("Movie 1");
        when(mockRental1.getType()).thenReturn("Type 1");
        when(mockRental1.getCharge()).thenReturn(10.0);
        when(mockRental1.getVat()).thenReturn(2.0);

        when(mockRental2.getMovie()).thenReturn(mockMovie2);
        when(mockRental2.getMovie().getTitle()).thenReturn("Movie 2");
        when(mockRental2.getType()).thenReturn("Type 2");
        when(mockRental2.getCharge()).thenReturn(15.0);
        when(mockRental2.getVat()).thenReturn(3.0);

        rentals.add(mockRental1);
        rentals.add(mockRental2);
        customer.setRentals(rentals);

        String expectedStatement = "Rental Record for Test Customer\n" +
                "\tMovie 1Type 1\t10.0\tincl.VAT 2.0\n" +
                "\tMovie 2Type 2\t15.0\tincl.VAT 3.0\n" +
                "Amount owed is 25.0\n" +
                "You earned 0 frequent renter points";
        String actualStatement = customer.statement();

        assertEquals(expectedStatement, actualStatement);
    }

    @Test
    public void testHtmlStatement_emptyRentals() {
        String expectedHtmlStatement = "<h1>Rentals for <em>Test Customer</em></h1>\n" +
                "<p></p>\n" +
                "<p>You owe <em>0.0</em></p>\n" +
                "<p>On this rental you earned <em>0</em> frequent renter points</p>";
        String actualHtmlStatement = customer.htmlStatement();

        assertEquals(expectedHtmlStatement, actualHtmlStatement);
    }

    @Test
    public void testHtmlStatement_multipleRentals() {
        Rental mockRental1 = mock(Rental.class);
        Rental mockRental2 = mock(Rental.class);
        Movie mockMovie1 = mock(Movie.class);
        Movie mockMovie2 = mock(Movie.class);

        when(mockRental1.getMovie()).thenReturn(mockMovie1);
        when(mockRental1.getMovie().getTitle()).thenReturn("Movie 1");
        when(mockRental1.getType()).thenReturn("Type 1");
        when(mockRental1.getCharge()).thenReturn(10.0);
        when(mockRental1.getVat()).thenReturn(2.0);

        when(mockRental2.getMovie()).thenReturn(mockMovie2);
        when(mockRental2.getMovie().getTitle()).thenReturn("Movie 2");
        when(mockRental2.getType()).thenReturn("Type 2");
        when(mockRental2.getCharge()).thenReturn(15.0);
        when(mockRental2.getVat()).thenReturn(3.0);

        rentals.add(mockRental1);
        rentals.add(mockRental2);
        customer.setRentals(rentals);

        String expectedHtmlStatement = "<h1>Rentals for <em>Test Customer</em></h1>\n" +
                "<p>Movie 1 Type 1: 10.0 incl.VAT:2.0<br>\n" +
                "Movie 2 Type 2: 15.0 incl.VAT:3.0<br>\n" +
                "</p>\n" +
                "<p>You owe <em>25.0</em></p>\n" +
                "<p>On this rental you earned <em>0</em> frequent renter points</p>";
        String actualHtmlStatement = customer.htmlStatement();

        assertEquals(expectedHtmlStatement, actualHtmlStatement);
    }

        @Test
        public void testGetTotalCharge_emptyRentals() {
            double expectedTotalCharge = 0.0;
            double actualTotalCharge = customer.getTotalCharge();

            assertEquals(expectedTotalCharge, actualTotalCharge);
        }

        @Test
        public void testGetTotalCharge_multipleRentals() {
            Rental mockRental1 = mock(Rental.class);
            Rental mockRental2 = mock(Rental.class);

            when(mockRental1.getCharge()).thenReturn(10.0);
            when(mockRental2.getCharge()).thenReturn(15.0);

            rentals.add(mockRental1);
            rentals.add(mockRental2);
            customer.setRentals(rentals);

            double expectedTotalCharge = 25.0;
            double actualTotalCharge = customer.getTotalCharge();

            assertEquals(expectedTotalCharge, actualTotalCharge);
        }

        @Test
        public void testGetTotalFrequentRenterPoints_emptyRentals() {
            int expectedTotalPoints = 0;
            int actualTotalPoints = customer.getTotalFrequentRenterPoints();

            assertEquals(expectedTotalPoints, actualTotalPoints);
        }

        @Test
        public void testGetTotalFrequentRenterPoints_multipleRentals() {
            Rental mockRental1 = mock(Rental.class);
            Rental mockRental2 = mock(Rental.class);

            when(mockRental1.getFrequentRenterPoints()).thenReturn(1);
            when(mockRental2.getFrequentRenterPoints()).thenReturn(2);

            rentals.add(mockRental1);
            rentals.add(mockRental2);
            customer.setRentals(rentals);

            int expectedTotalPoints = 3;
            int actualTotalPoints = customer.getTotalFrequentRenterPoints();

            assertEquals(expectedTotalPoints, actualTotalPoints);
        }

}