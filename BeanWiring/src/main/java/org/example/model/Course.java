package org.example.model;

public class Course {
    private String id;
    private String name;
    private String professorName;

    //Default constructor
    public Course(){}

    public Course(String id, String name, String professorName) {
        this.id = id;
        this.name = name;
        this.professorName = professorName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfessorName() {
        return professorName;
    }

    public void setProfessorName(String professorName) {
        this.professorName = professorName;
    }
}
