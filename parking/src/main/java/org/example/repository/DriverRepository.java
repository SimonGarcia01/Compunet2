package org.example.repository;

import org.example.model.Driver;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class DriverRepository {

    private final Map<String, Driver> drivers = new HashMap<>();

    public Collection<Driver> getDrivers() {
        return drivers.values();
    }

    public Driver searchDriver(String identificationNumber){
        return drivers.get(identificationNumber);
    }

    public void addDriver(String identificationNumber, Driver driver) {
        drivers.put(identificationNumber,driver);
    }

    public boolean removeDriver(String identificationNumber) {
        if(drivers.containsKey(identificationNumber)){
            drivers.remove(identificationNumber);
            return true;
        }
        return false;
    }
}
