package org.example.model;

public class Vehicle {
    String id;
    String plate;
    String CC;
    String brand;
    String model;
    String motorID;
    FuelType fuelType;
    String driverID;

    public Vehicle() {
    }

    public Vehicle(String id, String plate, String CC, String brand, String model, String motorID, String fuelType, String driverID) {
        this.id = id;
        this.plate = plate;
        this.CC = CC;
        this.brand = brand;
        this.model = model;
        this.motorID = motorID;
        this.fuelType = FuelType.returnIDType(fuelType);
        this.driverID = driverID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getCC() {
        return CC;
    }

    public void setCC(String CC) {
        this.CC = CC;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMotorID() {
        return motorID;
    }

    public void setMotorID(String motorID) {
        this.motorID = motorID;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public String getDriverID() {
        return driverID;
    }

    public void setDriverID(String driverID) {
        this.driverID = driverID;
    }
}