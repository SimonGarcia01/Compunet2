package org.example.introspringboot.api.v1.mappers;

import org.example.introspringboot.api.v1.dto.CourseProfessorResponse;
import org.example.introspringboot.entity.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ProfessorMapper.class)
public interface CourseMapper {

    //From Entity to DTO
    //Within the Course Entity, the professor variable = "professor"
    //Within the CourseDTO (CourseProfessorResponse), the professor variable = "professor"
    @Mapping(source="professor", target="professorDTO")
    //Forgot I had changed the course name within the course DTO
    @Mapping(source="name", target="courseName")
    public CourseProfessorResponse toDto(Course course);

}
