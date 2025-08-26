package org.example.respository;

import org.example.model.Passenger;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PassengerRepository {
    private final List<Passenger> passengers;

    public PassengerRepository() {
        this.passengers = new ArrayList<>();
    }

    public List<Passenger> getPassengers(){
        return passengers;
    }

    public void addPassenger(Passenger passenger){
        this.passengers.add(passenger);
    }
}
