package org.example.introspringboot.api.v1.dto;

public class CourseResponse {
    private Integer id;
    private String name;
    private int credits;

    public CourseResponse() {
        //Default constructor
    }

    public CourseResponse(Integer id, String name, int credits) {
        this.id = id;
        this.name = name;
        this.credits = credits;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }
}
