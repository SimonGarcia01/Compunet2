package org.example.repository;

import org.example.model.Driver;
import org.example.model.Vehicle;
import org.example.service.VehicleService;

import java.util.Collection;
import java.util.Map;
import java.util.HashMap;

public class VehicleRepository {

    private final Map<String, Vehicle> vehicles = new HashMap<>();

    public Collection<Vehicle> getVehicles() {
        return vehicles.values();
    }

    public Vehicle searchVehicle(String plate){
        return vehicles.get(plate);
    }

    public void addVehicle(String plate, Vehicle vehicle) {
        vehicles.put(plate,vehicle);
    }

    public boolean removeVehicle(String plate) {
        if(vehicles.containsKey(plate)){
            vehicles.remove(plate);
            return true;
        }
        return false;
    }
}