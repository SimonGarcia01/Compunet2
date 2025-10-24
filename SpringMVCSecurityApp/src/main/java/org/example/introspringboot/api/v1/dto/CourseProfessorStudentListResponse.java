package org.example.introspringboot.api.v1.dto;

import java.util.List;

public class CourseProfessorStudentListResponse {

    private String name;
    private ProfessorDTO professorDTO;
    private List<StudentDTO> studentDTOs;

    public CourseProfessorStudentListResponse() {
        //Default constructor
    }

    public CourseProfessorStudentListResponse(String name, ProfessorDTO professorDTO, List<StudentDTO> studentDTOs) {
        this.name = name;
        this.professorDTO = professorDTO;
        this.studentDTOs = studentDTOs;
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

    public List<StudentDTO> getStudentDTOs() {
        return studentDTOs;
    }

    public void setStudentDTOs(List<StudentDTO> studentNames) {
        this.studentDTOs = studentNames;
    }
}
