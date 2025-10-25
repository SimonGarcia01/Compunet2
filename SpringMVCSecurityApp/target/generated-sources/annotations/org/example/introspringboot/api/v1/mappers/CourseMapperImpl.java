package org.example.introspringboot.api.v1.mappers;

import javax.annotation.processing.Generated;
import org.example.introspringboot.api.v1.dto.CourseProfessorResponse;
import org.example.introspringboot.api.v1.dto.CourseProfessorStudentListResponse;
import org.example.introspringboot.api.v1.dto.CourseResponse;
import org.example.introspringboot.entity.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-24T22:52:57-0500",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.8 (Oracle Corporation)"
)
@Component
public class CourseMapperImpl implements CourseMapper {

    @Autowired
    private ProfessorMapper professorMapper;

    @Override
    public CourseProfessorStudentListResponse toCourseProfessorList(Course course) {
        if ( course == null ) {
            return null;
        }

        CourseProfessorStudentListResponse courseProfessorStudentListResponse = new CourseProfessorStudentListResponse();

        courseProfessorStudentListResponse.setProfessorDTO( professorMapper.toDto( course.getProfessor() ) );
        courseProfessorStudentListResponse.setName( course.getName() );

        return courseProfessorStudentListResponse;
    }

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

    @Override
    public CourseResponse toBasicCourse(Course course) {
        if ( course == null ) {
            return null;
        }

        CourseResponse courseResponse = new CourseResponse();

        courseResponse.setId( course.getId() );
        courseResponse.setName( course.getName() );
        courseResponse.setCredits( course.getCredits() );

        return courseResponse;
    }
}
