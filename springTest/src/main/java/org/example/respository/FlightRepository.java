package org.example.respository;

import org.example.model.Flight;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FlightRepository {
    private final List<Flight> flights;

    public FlightRepository() {
        this.flights = new ArrayList<>();
    }

    public List<Flight> getFlights(){
        return flights;
    }

    public void addFlight(Flight flight){
        this.flights.add(flight);
    }
}
