package org.example.introspringboot.api.v1.dto;

import java.util.List;

public class StudentOnlyCoursesResponse {
    List<CourseResponse> courses;

    public StudentOnlyCoursesResponse() {
        //Default Constructor
    }

    public StudentOnlyCoursesResponse(List<CourseResponse> courses) {
        this.courses = courses;
    }

    public List<CourseResponse> getCourses() {
        return courses;
    }

    public void setCourses(List<CourseResponse> courses) {
        this.courses = courses;
    }
}
