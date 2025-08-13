package de.uniba.wiai.dsg.ajp.assignment3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MovieTest {

    @Test
    public void regularHasBeenSetIntoEnum() {
        Movie movie = new Movie("Regular Movies", Movie.priceCode.REGULAR, "HD");
        assertEquals(2.0,movie.getCharge(2),"Charge should be 2 with 2 days rent");
        assertEquals(3.5,movie.getCharge(3),"Charge should be 3.5 with 3 days rent");
    }

    @Test
    public void newReleaseHasBeenSetIntoEnum(){
        Movie movie = new Movie("New Release Movies", Movie.priceCode.NEW_RELEASE, "HD");
        assertEquals(3.0,movie.getCharge(1),"Charge should be 3 with 1 day rent");
        assertEquals(6.0,movie.getCharge(2),"Charge should be 6 with 2 days rent");
    }

    @Test
    public void childrenHasBeenSetIntoEnum(){
        Movie movie = new Movie("Children Movies", Movie.priceCode.CHILDREN, "HD");
        assertEquals(1.5,movie.getCharge(3),"Charge should be 1.5 with 3 days rent");
        assertEquals(3.0,movie.getCharge(4),"Charge should be 3 with 4 days rent");
    }

    @Test
    public void fourKIsTwoMoreThanHd(){
        Movie movieHd = new Movie("Movie Title", Movie.priceCode.REGULAR, "HD");
        Movie movie4k = new Movie("Movie Title", Movie.priceCode.REGULAR, "4K");
        double chargeHd = movieHd.getCharge(4);
        double charge4k = chargeHd + 2.0;
        assertEquals(charge4k,movie4k.getCharge(4),"Charge of 4K should be 2 more than HD");
    }

    @Test
    public void imageQualityIsAfterMovieTitle(){
        Movie movieHd = new Movie("Movie Title", Movie.priceCode.REGULAR, "HD");
        Movie movie4k = new Movie("Movie Title", Movie.priceCode.REGULAR, "4K");
        assertEquals("Movie Title (HD)",movieHd.getTitle());
        assertEquals("Movie Title (4K)",movie4k.getTitle());
    }
}