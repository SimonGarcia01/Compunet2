package org.example.introspringboot.api.v1.mappers;

import org.example.introspringboot.api.v1.dto.StudentDTO;
import org.example.introspringboot.entity.Student;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    public Student toEntity(Student student);
    public StudentDTO toDto(Student student);
}
