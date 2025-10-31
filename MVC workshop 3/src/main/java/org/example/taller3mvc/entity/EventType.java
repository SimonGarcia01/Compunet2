package org.example.taller3mvc.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;
// Da error el JsonIgnore, no se por que
//import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "event_types")
public class EventType {

    // Primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer eventTypeId;

    // Foreign keys
    @JsonIgnore
    @OneToMany(mappedBy = "eventType", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Event> eventsList;

    //Attributes
    @Column(nullable= false, length=30)
    private String name;
    @Column(nullable = true, length=200)
    private String description;

    public EventType() {
        //default constructor
    }

    public EventType(Integer eventTypeId, String name, String description) {
        this.eventTypeId = eventTypeId;
        this.name = name;
        this.description = description;
    }

    // Getters and Setters
    public Integer getEventTypeId() {
        return eventTypeId;
    }

    public void setEventTypeId(Integer eventTypeId) {
        this.eventTypeId = eventTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Event> getEventsList() {
        return eventsList;
    }

    public void setEventsList(List<Event> eventsList) {
        this.eventsList = eventsList;
    }

}
