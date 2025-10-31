package org.example.taller3mvc.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="available_spaces")
public class AvailableSpace {

    // Primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer spaceId;

    // Foreign keys
    @JsonIgnore
    @OneToMany(mappedBy = "availableSpace", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Event> eventsList;

    //Attributes
    @Column(nullable= false, length = 30)
    private String name;
    @Column(nullable = false, length = 100)
    private String location;
    @Column(nullable= false)
    private int locationMaxAttendees;

    public AvailableSpace() {
        //default constructor
    }

    public AvailableSpace(Integer spaceId, String name, String location, int locationMaxAttendees) {
        this.spaceId = spaceId;
        this.name = name;
        this.location = location;
        this.locationMaxAttendees = locationMaxAttendees;
    }

    // Getters and Setters
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

    public List<Event> getEventsList() {
        return eventsList;
    }

    public void setEventsList(List<Event> eventsList) {
        this.eventsList = eventsList;
    }

}
