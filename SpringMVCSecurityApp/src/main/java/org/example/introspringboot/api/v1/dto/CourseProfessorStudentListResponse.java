package org.example.introspringboot.api.v1.dto;

import java.util.List;

public class CourseProfessorStudentListResponse {

    private String name;
    private ProfessorDTO professorDTO;
    private List<String> studentNames;

    public CourseProfessorStudentListResponse() {
        //Default constructor
    }

    public CourseProfessorStudentListResponse(String name, ProfessorDTO professorDTO, List<String> studentNames) {
        this.name = name;
        this.professorDTO = professorDTO;
        this.studentNames = studentNames;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProfessorDTO getProfessorDTO() {
        return professorDTO;
    }

    public void setProfessorDTO(ProfessorDTO professorDTO) {
        this.professorDTO = professorDTO;
    }

    public List<String> getStudentNames() {
        return studentNames;
    }

    public void setStudentNames(List<String> studentNames) {
        this.studentNames = studentNames;
    }
}
