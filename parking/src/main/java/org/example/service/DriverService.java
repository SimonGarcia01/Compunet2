package org.example.service;

import org.example.model.Driver;
import org.example.model.IDType;
import org.example.model.Vehicle;
import org.example.repository.DriverRepository;
import org.example.repository.VehicleRepository;

import java.util.ArrayList;
import java.util.Collection;

public class DriverService {

    private final DriverRepository driverRepository;
    private final VehicleRepository vehicleRepository;

    private int driverIdCounter = 1;

    public DriverService(DriverRepository driverRepository, VehicleRepository vehicleRepository) {
        this.driverRepository = driverRepository;
        this.vehicleRepository = vehicleRepository;
    }

    public boolean addDriver(String name, String occupation, String idType, String identificationNumber) {
        Driver driver = driverRepository.searchDriver(identificationNumber);

        if(driver == null){
            driverRepository.addDriver(identificationNumber, new Driver(String.valueOf(driverIdCounter), name, occupation, idType, identificationNumber));
            driverIdCounter++;
            return true;
        }

        return false;
    }

    public Driver getDriverbyIdentificationNumber(String identificationNumber) {
        return driverRepository.searchDriver(identificationNumber);
    }

    public ArrayList<Vehicle> getVehiclesByDriverId(String driverId) {
        ArrayList<Vehicle> result = new ArrayList<>();
        for (Vehicle v : vehicleRepository.getVehicles()) {
            if (v.getDriverID().equals(driverId)) {
                result.add(v);
            }
        }
        return result;
    }

    public Collection<Driver> getDrivers() {
        return driverRepository.getDrivers();
    }

    public String[] getIDTypes(){
        return IDType.getIDTypes();
    }
}