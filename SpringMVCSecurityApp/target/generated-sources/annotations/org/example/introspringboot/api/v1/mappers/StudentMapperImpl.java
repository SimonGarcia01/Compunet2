package org.example.introspringboot.api.v1.mappers;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.example.introspringboot.api.v1.dto.StudentDTO;
import org.example.introspringboot.entity.Student;
import org.example.introspringboot.entity.StudentCourse;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-24T22:52:57-0500",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.8 (Oracle Corporation)"
)
@Component
public class StudentMapperImpl implements StudentMapper {

    @Override
    public Student toEntity(Student student) {
        if ( student == null ) {
            return null;
        }

        Student student1 = new Student();

        student1.setId( student.getId() );
        List<StudentCourse> list = student.getStudentCourses();
        if ( list != null ) {
            student1.setStudentCourses( new ArrayList<StudentCourse>( list ) );
        }
        student1.setName( student.getName() );
        student1.setCode( student.getCode() );
        student1.setProgram( student.getProgram() );

        return student1;
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
