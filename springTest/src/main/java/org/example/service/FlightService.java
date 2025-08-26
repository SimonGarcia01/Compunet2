package org.example.service;

import org.example.model.Flight;
import org.example.respository.FlightRepository;
import org.example.respository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightService {

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private PassengerRepository passengerRepository;

    public void addFlight(String id,
                          String origin,
                          String destination,
                          String date){
        flightRepository.addFlight(new Flight(id, origin, destination, date));
    }

    public List<Flight> getFlights(){
        return flightRepository.getFlights();
    }
}
