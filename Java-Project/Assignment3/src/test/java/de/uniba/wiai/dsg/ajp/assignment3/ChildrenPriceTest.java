package de.uniba.wiai.dsg.ajp.assignment3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChildrenPriceTest {

    private ChildrenPrice price;

    @BeforeEach
    public void setup() {
        price = new ChildrenPrice();
    }
    @Test
    public void testGetCharge_daysRentedLessThanThree() {
        double charge = price.getCharge(2);
        assertEquals(1.5,charge,0.001);
    }

    @Test
    public void testGetCharge_daysRentedMoreThanThree() {
        double charge = price.getCharge(4);
        assertEquals(3.0, charge, 0.001);
    }

    @Test
    public void testGetCharge_withInvalidDaysRented() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            price.getCharge(-1));

        String expectedMessage = "days rented must be >=1";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }


}