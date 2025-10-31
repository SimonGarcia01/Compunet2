package org.example.introspringboot.api.v1.dto;

public class CourseRequest {

    private String name;
    private int credits;
    private Integer professorId;

    public CourseRequest() {
        //Default constructor
    }

    public CourseRequest(String name, int credits, Integer professorId) {
        this.name = name;
        this.credits = credits;
        this.professorId = professorId;
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

    public Integer getProfessorId() {
        return professorId;
    }

    public void setProfessorId(Integer professorId) {
        this.professorId = professorId;
    }
}
