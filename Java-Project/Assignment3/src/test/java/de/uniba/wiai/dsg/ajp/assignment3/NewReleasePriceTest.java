package de.uniba.wiai.dsg.ajp.assignment3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NewReleasePriceTest {

    private NewReleasePrice price;

    @BeforeEach
    public void setUp() {price = new NewReleasePrice();}

    @Test
    public void testGetCharge_daysRentedMoreThanZero() {
        double charge = price.getCharge(3);
        assertEquals(9,charge,0.001);
    }

    @Test
    public void testGetCharge_withInvalidDaysRented() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            price.getCharge(-1));

        String expectedMessage = "days rented must be >=1";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testGetFrequentRenterPoints_daysRentedMoreThanOne() {
        int points = price.getFrequentRenterPoints(3);
        assertEquals(2,points);

    }

    @Test
    public void testGetFrequentRenterPoints_daysRentedEqualsOne() {
        int points = price.getFrequentRenterPoints(1);
        assertEquals(1,points);

    }

    @Test
    public void testGetFrequentRenterPoints_withInvalidDaysRented() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            price.getFrequentRenterPoints(-1));

        String expectedMessage = "days rented must be >=1";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}