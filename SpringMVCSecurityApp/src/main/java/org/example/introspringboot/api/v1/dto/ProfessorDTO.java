package org.example.introspringboot.api.v1.dto;

public class ProfessorDTO {
    private Integer id;
    private String name;

    public ProfessorDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public ProfessorDTO() {
        //Default constructor
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
}

//{"id":1, "name":"ProfessorName"}