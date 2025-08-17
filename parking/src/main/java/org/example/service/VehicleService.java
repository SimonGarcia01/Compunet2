package org.example.service;

import org.example.model.Driver;
import org.example.model.FuelType;
import org.example.model.Vehicle;
import org.example.repository.DriverRepository;
import org.example.repository.VehicleRepository;

import java.util.Collection;

public class VehicleService {

    private DriverRepository driverRepository;
    private VehicleRepository vehicleRepository;

    private int vehicleIdCounter = 1;


    public VehicleService() {
        //Default constructor
    }

    public VehicleService(DriverRepository driverRepository, VehicleRepository vehicleRepository) {
        this.driverRepository = driverRepository;
        this.vehicleRepository = vehicleRepository;
    }



    public String addVehicleToDriver(String identificationNumber, String id, String plate, String CC, String brand, String model, String motorID, String fuelType) {
        Driver driver = driverRepository.searchDriver(identificationNumber);

        if(driver != null){
            Vehicle vehicle = vehicleRepository.searchVehicle(plate);
            if(vehicle != null){
                return "The entered plate is already registered to another driver.";
            }

            vehicle = new Vehicle(String.valueOf(this.vehicleIdCounter), plate, CC, brand, model, motorID, fuelType, identificationNumber);
            this.vehicleIdCounter++;

            vehicleRepository.addVehicle(plate, vehicle);

            return "The vehicle has been added successfully.";
        }

        return "The entered driver ID doesn't exist.";
    }

    public Collection<Vehicle> getAllVehicles(){
        return vehicleRepository.getVehicles();
    }

    public Vehicle getVehicleByPlate(String plate) {
        return vehicleRepository.searchVehicle(plate);
    }

    public String deleteVehicleByPlate(String plate) {
        if(vehicleRepository.removeVehicle(plate)){
            return "The vehicl has been removed successfully";
        } else {
            return "The vehicle was not found";
        }
    }
}