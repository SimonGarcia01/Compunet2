package org.example.finalproject.api.v1.dtos;

public class AvailableSpaceRequest {

    private String name;
    private String location;
    private int locationMaxAttendees;

    public AvailableSpaceRequest() {
        //Default constructor
    }

    public AvailableSpaceRequest(String name, String location, int locationMaxAttendees) {
        this.name = name;
        this.location = location;
        this.locationMaxAttendees = locationMaxAttendees;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getLocationMaxAttendees() {
        return locationMaxAttendees;
    }

    public void setLocationMaxAttendees(int locationMaxAttendees) {
        this.locationMaxAttendees = locationMaxAttendees;
    }
}
