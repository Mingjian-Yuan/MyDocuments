package de.uniba.wiai.dsg.ajp.assignment3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class RentalAdditionalTest {



    @Test
    public void rentalConstructor_validArguments() {
        Rental rental = new Rental("DVD", 3);
        assertEquals("DVD", rental.getType());
        assertEquals(3, rental.getDaysRented());
    }

    @Test
    public void rentalConstructor_invalidType() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Rental("VHS", 3);
        });
    }

    @Test
    public void rentalConstructor_daysRentedLessThanOne() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Rental("DVD", 0);
        });
    }


    @Test
    public void testSetDaysRented_validDaysRented() {
        Rental rental = new Rental("DVD", 3);
        rental.setDaysRented(5);
        assertEquals(5, rental.getDaysRented());
    }

    @Test
    public void testSetDaysRented_daysRentedLessThanOne() {
        Rental rental = new Rental("DVD", 3);
        assertThrows(IllegalArgumentException.class, () -> {
            rental.setDaysRented(0);
        });
    }

    @Test
    public void testSetDaysRented_streamingType() {
        Rental rental = new Rental("Streaming", 1);
        assertThrows(IllegalArgumentException.class, () -> {
            rental.setDaysRented(2);
        });
    }

    @Test
    public void testGetCharge_regular() {
        Movie movie = new Movie("Test Movie", Movie.priceCode.REGULAR, Movie.HD);
        Rental rental = new Rental("DVD", 3);
        rental.setMovie(movie);

        double expectedCharge =3.5;

        assertEquals(expectedCharge, rental.getCharge(), 0.01);
    }

    //getCharge的测试因为code是enu（常量）而要使用黑盒测试
    @Test
    public void testGetCharge_newRelease() {
        Movie movie = new Movie("Test Movie", Movie.priceCode.NEW_RELEASE, Movie.HD);
        Rental rental = new Rental("DVD", 3);
        rental.setMovie(movie);

        double expectedCharge =9;

        assertEquals(expectedCharge, rental.getCharge(), 0.01);
    }

    @Test
    public void testGetCharge_children() {
        Movie movie = new Movie("Test Movie", Movie.priceCode.CHILDREN, Movie.HD);
        Rental rental = new Rental("DVD", 3);
        rental.setMovie(movie);

        double expectedCharge =1.5;

        assertEquals(expectedCharge, rental.getCharge(), 0.01);
    }

}