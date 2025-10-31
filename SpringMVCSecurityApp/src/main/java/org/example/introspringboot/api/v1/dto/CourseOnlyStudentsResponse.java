package org.example.introspringboot.api.v1.dto;

import java.util.List;

public class CourseOnlyStudentsResponse {
    private List<StudentDTO> students;

    public CourseOnlyStudentsResponse() {
        //Default constructor
    }

    public CourseOnlyStudentsResponse(List<StudentDTO> students) {
        this.students = students;
    }

    public List<StudentDTO> getStudents() {
        return students;
    }

    public void setStudents(List<StudentDTO> students) {
        this.students = students;
    }
}
