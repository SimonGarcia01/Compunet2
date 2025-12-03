package org.example.finalproject.api.v1.dtos;

public class AvailableSpaceResponse {
    private Integer spaceId;
    private String name;
    private String location;
    private int locationMaxAttendees;

    public AvailableSpaceResponse() {
        //Default constructor
    }

    public AvailableSpaceResponse(Integer spaceId, String name, String location, int locationMaxAttendees) {
        this.spaceId = spaceId;
        this.name = name;
        this.location = location;
        this.locationMaxAttendees = locationMaxAttendees;
    }

    public Integer getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(Integer spaceId) {
        this.spaceId = spaceId;
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
