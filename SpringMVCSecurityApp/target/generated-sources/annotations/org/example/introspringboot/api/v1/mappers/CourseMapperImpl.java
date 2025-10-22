package org.example.introspringboot.api.v1.mappers;

import javax.annotation.processing.Generated;
import org.example.introspringboot.api.v1.dto.CourseProfessorResponse;
import org.example.introspringboot.entity.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-22T12:37:32-0500",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.8 (Oracle Corporation)"
)
@Component
public class CourseMapperImpl implements CourseMapper {

    @Autowired
    private ProfessorMapper professorMapper;

    @Override
    public CourseProfessorResponse toDto(Course course) {
        if ( course == null ) {
            return null;
        }

        CourseProfessorResponse courseProfessorResponse = new CourseProfessorResponse();

        courseProfessorResponse.setProfessorDTO( professorMapper.toDto( course.getProfessor() ) );
        courseProfessorResponse.setCourseName( course.getName() );
        courseProfessorResponse.setCredits( course.getCredits() );

        return courseProfessorResponse;
    }
}
