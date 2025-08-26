package org.example.service;

import org.example.model.Flight;
import org.example.model.Passenger;
import org.example.respository.FlightRepository;
import org.example.respository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class PassengerService {

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private FlightRepository flightRepository;

    public void addPassenger(String flightId,
                               String passengerId,
                               String name,
                               String passportId){

        Map<String, Passenger> sameFlightPassengers = new HashMap<>();

        for(Passenger passenger : passengerRepository.getPassengers()){
            if(passenger.getFlightId().equals(flightId)){
                sameFlightPassengers.put(passenger.getPassportId(), passenger);
            }
        }

        if(!sameFlightPassengers.containsKey(passportId)){
            passengerRepository.addPassenger(new Passenger(passengerId, name, passportId, flightId));
            System.out.println(passengerRepository.getPassengers().size());
        }
    }

    public List<Flight> getFlights(){
        return flightRepository.getFlights();
    }

    public List<Passenger> getPassengers(){
        return passengerRepository.getPassengers();
    }

}
