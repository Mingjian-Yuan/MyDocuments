package de.uniba.wiai.dsg.ajp.assignment3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class CustomerTest {

    @Test
    public void vatIsCorrectCalculated(){
        Rental rental = new Rental("DVD",1);
        Movie movie = new Movie("Movie Title", Movie.priceCode.NEW_RELEASE, "HD");
        rental.setMovie(movie);
        rental.setDaysRented(100);
        assertEquals(57,rental.getVat());
    }

    @Test
    public void vatIsContainedInStatement(){
        Customer customer = new Customer("Test Customer");
        Rental rental = new Rental("DVD",1);
        Movie movie = new Movie("Movie Title", Movie.priceCode.NEW_RELEASE, "HD");
        rental.setMovie(movie);
        rental.setDaysRented(100);
        customer.getRentals().add(rental);
        String statement = customer.statement();
        assertTrue(statement.contains("incl.VAT "+rental.getVat()));
    }

    @Test
    public void vatIsContainedInHtmlStatement(){
        Customer customer = new Customer("Test Customer");
        Rental rental = new Rental("DVD",1);
        Movie movie = new Movie("Movie Title", Movie.priceCode.NEW_RELEASE, "HD");
        rental.setMovie(movie);
        rental.setDaysRented(100);
        customer.getRentals().add(rental);
        String statement = customer.htmlStatement();
        assertTrue(statement.contains(" incl.VAT:"+rental.getVat()));
    }

    @Test
    public void typeIsContainedInStatement(){
        Customer customer = new Customer("Test Customer");
        Rental rental = new Rental("DVD",1);
        Movie movie = new Movie("Movie Title", Movie.priceCode.NEW_RELEASE, "HD");
        rental.setMovie(movie);
        rental.setDaysRented(100);
        customer.getRentals().add(rental);
        String statement = customer.statement();
        assertTrue(statement.contains("DVD"));
    }

    @Test
    public void typeIsContainedInHtmlStatement(){
        Customer customer = new Customer("Test Customer");
        Rental rental = new Rental("DVD",1);
        Movie movie = new Movie("Movie Title", Movie.priceCode.NEW_RELEASE, "HD");
        rental.setMovie(movie);
        rental.setDaysRented(100);
        customer.getRentals().add(rental);
        String statement = customer.htmlStatement();
        assertTrue(statement.contains("DVD"));
    }

}