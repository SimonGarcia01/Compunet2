package org.example.introspringboot.api.v1.dto;

public class CourseEnrollmentResponse {
    private Integer id;
    private String name;
    private int credits;
    private int enrolledStudents;

    public CourseEnrollmentResponse() {
        //Default Constructor
    }

    public CourseEnrollmentResponse(Integer id, String name, int credits, int enrolledStudents) {
        this.id = id;
        this.name = name;
        this.credits = credits;
        this.enrolledStudents = enrolledStudents;
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

    public int getEnrolledStudents() {
        return enrolledStudents;
    }

    public void setEnrolledStudents(int enrolledStudents) {
        this.enrolledStudents = enrolledStudents;
    }
}
