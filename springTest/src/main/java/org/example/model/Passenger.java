package org.example.model;

public class Passenger {
    private String id;
    private String name;
    private String passportId;
    private String flightId;

    public Passenger(String id, String name, String passportId, String flightId) {
        this.id = id;
        this.name = name;
        this.passportId = passportId;
        this.flightId = flightId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassportId() {
        return passportId;
    }

    public void setPassportId(String passportId) {
        this.passportId = passportId;
    }

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }
}
