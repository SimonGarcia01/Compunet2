package org.example.introspringboot.api.v1.dto;

public class StudentDTO {

    private String name;
    private String code;
    private String program;

    public StudentDTO() {
        //Default constructor
    }

    public StudentDTO(String name, String code, String program) {
        this.name = name;
        this.code = code;
        this.program = program;
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
