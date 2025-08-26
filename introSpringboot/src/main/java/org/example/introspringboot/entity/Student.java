package org.example.introspringboot.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student {

    //int so IDs can be made easily
    //It can be string, but another mechanism of autogeneration must be used
    @Id
    //This is so the pk automatically changes the ID by incrementing
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String code;
    private String program;

    public Student(int id, String name, String code, String program) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.program = program;
    }

    public Student() {
        //Default constructor
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
}
