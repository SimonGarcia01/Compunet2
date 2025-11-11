package org.example.introspringboot.api.v1.dto;

import java.util.List;

public class StudentCompleteResponse {
    private Integer id;
    private String name;
    private String code;
    private String program;
    private List<CourseResponse> courses;

    public StudentCompleteResponse() {
        //Default Constructor
    }

    public StudentCompleteResponse(Integer id, String name, String code, String program, List<CourseResponse> courses) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.program = program;
        this.courses = courses;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public List<CourseResponse> getCourses() {
        return courses;
    }

    public void setCourses(List<CourseResponse> courses) {
        this.courses = courses;
    }
}
