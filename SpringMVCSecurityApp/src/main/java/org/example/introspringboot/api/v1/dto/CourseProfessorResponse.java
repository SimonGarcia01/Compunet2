package org.example.introspringboot.api.v1.dto;

public class CourseProfessorResponse {
    private String courseName;
    private int credits;
    private ProfessorDTO professorDTO;

    public CourseProfessorResponse(String courseName, int credits, ProfessorDTO professorDTO) {
        this.courseName = courseName;
        this.credits = credits;
        this.professorDTO = professorDTO;
    }

    public CourseProfessorResponse() {
        //Default Constructor
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public ProfessorDTO getProfessorDTO() {
        return professorDTO;
    }

    public void setProfessorDTO(ProfessorDTO professorDTO) {
        this.professorDTO = professorDTO;
    }
}
