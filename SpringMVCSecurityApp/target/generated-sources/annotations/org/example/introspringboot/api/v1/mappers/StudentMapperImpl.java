package org.example.introspringboot.api.v1.mappers;

import javax.annotation.processing.Generated;
import org.example.introspringboot.api.v1.dto.StudentDTO;
import org.example.introspringboot.entity.Student;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-31T12:13:52-0500",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.8 (Oracle Corporation)"
)
@Component
public class StudentMapperImpl implements StudentMapper {

    @Override
    public Student toEntity(StudentDTO studentDTO) {
        if ( studentDTO == null ) {
            return null;
        }

        Student student = new Student();

        student.setName( studentDTO.getName() );
        student.setCode( studentDTO.getCode() );
        student.setProgram( studentDTO.getProgram() );

        return student;
    }

    @Override
    public StudentDTO toDto(Student student) {
        if ( student == null ) {
            return null;
        }

        StudentDTO studentDTO = new StudentDTO();

        studentDTO.setName( student.getName() );
        studentDTO.setCode( student.getCode() );
        studentDTO.setProgram( student.getProgram() );

        return studentDTO;
    }
}
