package de.uniba.wiai.dsg.ajp.assignment3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RentalTest {

    @Test
    public void streamingCanNotBeRentedMoreThanOneDay(){
        try{
            Rental rental = new Rental("Streaming",1);
            rental.setDaysRented(2);
            fail();
        }catch (IllegalArgumentException e){
            assertEquals("Cannot set days rented for streaming movies",e.getMessage());
        }
    }

}