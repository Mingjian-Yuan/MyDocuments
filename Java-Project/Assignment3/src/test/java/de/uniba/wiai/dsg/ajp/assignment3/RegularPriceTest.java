package de.uniba.wiai.dsg.ajp.assignment3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegularPriceTest {

    private RegularPrice price;

    @BeforeEach
    public void setup() {
        price = new RegularPrice();
    }

    @Test
    void testGetCharge_daysRentedMoreThanTwo() {
        double charge = price.getCharge(3);
        assertEquals(3.5,charge,0.001);

    }

    @Test
    void testGetCharge_daysRentedLessThanTwo() {
        double charge = price.getCharge(1);
        assertEquals(2,charge,0.001);

    }

    @Test
    void testGetCharge_withInvalidDaysRented() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                price.getCharge(-1));

        String expectedMessage = "days rented must be >=1";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }


}