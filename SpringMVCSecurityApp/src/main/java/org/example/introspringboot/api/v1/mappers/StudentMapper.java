package org.example.introspringboot.api.v1.mappers;

import org.example.introspringboot.api.v1.dto.StudentCompleteResponse;
import org.example.introspringboot.api.v1.dto.StudentDTO;
import org.example.introspringboot.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    public Student toEntity(StudentDTO studentDTO);
    public StudentDTO toDto(Student student);

    @Mapping(source="studentCourses", target="courses", ignore = true)
    public StudentCompleteResponse toDetailedStudent(Student student);
}
