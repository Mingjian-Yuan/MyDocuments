package de.uniba.wiai.dsg.ajp.assignment3;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
class MovieAdditionalTest {

    private String title;
    private Movie.priceCode priceCode;
    private String imageQuality;

    @BeforeEach
    void setUp() {
        title = "Test Movie";
        priceCode = Movie.priceCode.REGULAR;
        imageQuality = Movie.HD;
    }

    @Test
    void testConstructor_validInputs() {
        Movie movie = new Movie(title, priceCode, imageQuality);
        assertTrue(movie.getTitle().contains(title));
        assertTrue(movie.getTitle().contains(imageQuality));
        assertEquals(priceCode.ordinal(), movie.getPriceCode());
    }

    @Test
    void testConstructor_nullTitle() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Movie(null, priceCode, imageQuality);
        });
    }

    @Test
    void testConstructor_emptyTitle() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Movie("", priceCode, imageQuality);
        });
    }

    @Test
    void testConstructor_nullPriceCode() {
        assertThrows(NullPointerException.class, () -> {
            new Movie(title, null, imageQuality);
        });
    }

    @Test
    void testConstructor_invalidImageQuality() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Movie(title, priceCode, "INVALID");
        });
    }


    @Test
    public void testSetTitle_nullTitle() {
        Movie movie = new Movie("Movie Title", Movie.priceCode.REGULAR, Movie.HD);

        assertThrows(IllegalArgumentException.class, () ->
                movie.setTitle(null));
    }

    @Test
    public void testSetTitle_emptyTitle() {
        Movie movie = new Movie("Movie Title", Movie.priceCode.REGULAR, Movie.HD);

        assertThrows(IllegalArgumentException.class, () ->
                movie.setTitle(""));
    }


    @Test
    void testGetCharge_regularMovie() {
        Movie regularMovie = new Movie("Regular Movie", Movie.priceCode.REGULAR, Movie.HD);
        int daysRented = 5;
        double charge = regularMovie.getCharge(daysRented);
        double expectedCharge = 6.5;
        assertEquals(expectedCharge, charge);
    }

    @Test
    void testGetCharge_newReleaseMovie() {
        Movie newReleaseMovie = new Movie("New Release Movie", Movie.priceCode.NEW_RELEASE, Movie.fourK);
        int daysRented = 5;
        double charge = newReleaseMovie.getCharge(daysRented);
        double expectedCharge =17;
        assertEquals(expectedCharge, charge);
    }

    @Test
    void testGetCharge_childrenMovie() {
        Movie childrenMovie = new Movie("Children Movie", Movie.priceCode.CHILDREN, Movie.HD);
        int daysRented = 5;
        double charge = childrenMovie.getCharge(daysRented);
        double expectedCharge = 4.5;
        assertEquals(expectedCharge, charge);
    }

    @Test
    void testGetCharge_throwsExceptionForInvalidDaysRented() {
        Movie movie = new Movie("Regular Movie", Movie.priceCode.REGULAR, Movie.HD);
        int invalidDaysRented = 0;
        assertThrows(IllegalArgumentException.class, () -> movie.getCharge(invalidDaysRented));
    }


    @Test
    void testGetVat() {
        Movie movie = new Movie("Regular Movie", Movie.priceCode.REGULAR, Movie.HD);
        double vat = movie.getVat(5);
        double expectedVat = 1.2;
        assertEquals(expectedVat, vat);
    }

    @Test
    void testGetVat_throwsExceptionForInvalidDaysRented() {
        Movie movie = new Movie("Regular Movie", Movie.priceCode.REGULAR, Movie.HD);
        int invalidDaysRented = 0;
        assertThrows(IllegalArgumentException.class, () -> movie.getVat(invalidDaysRented));
    }

    // 测试setPriceCode()方法
    @Test
    void testSetPriceCode() {
        Movie movie = new Movie("Regular Movie", Movie.priceCode.REGULAR, Movie.HD);
        movie.setPriceCode(Movie.priceCode.NEW_RELEASE);
        assertEquals(Movie.priceCode.NEW_RELEASE.ordinal(), movie.getPriceCode());
    }

    @Test
    public void testGetFrequentRenterPoints_validDaysRented() {
        Movie movie = new Movie("Movie Title", Movie.priceCode.REGULAR, Movie.HD);

        int expectedPoints = 1;
        int actualPoints = movie.getFrequentRenterPoints(5);

        assertEquals(expectedPoints, actualPoints);
    }

    @Test
    public void testGetFrequentRenterPoints_invalidDaysRented() {
        Movie movie = new Movie("Movie Title", Movie.priceCode.REGULAR, Movie.HD);

        assertThrows(IllegalArgumentException.class, () ->
                movie.getFrequentRenterPoints(0));
    }
}
