package org.example.introspringboot.api.v1.mappers;

import org.example.introspringboot.api.v1.dto.CourseProfessorResponse;
import org.example.introspringboot.api.v1.dto.CourseProfessorStudentListResponse;
import org.example.introspringboot.api.v1.dto.CourseResponse;
import org.example.introspringboot.entity.Course;
import org.example.introspringboot.entity.StudentCourse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = ProfessorMapper.class)
public interface CourseMapper {

    //For the basic CourseProfessorDTO
    //From Entity to DTO
    //Within the Course Entity, the professor variable = "professor"
    //Within the CourseDTO (CourseProfessorResponse), the professor variable = "professor"
    @Mapping(source="professor", target="professorDTO")
    //Forgot I had changed the course name within the course DTO
    @Mapping(source="name", target="courseName")
    public CourseProfessorResponse toDto(Course course);

    //For the CourseProfessorStudentList DTO
    @Mapping(source = "professor", target = "professorDTO")
    @Mapping(source = "studentCourses", target = "studentNames")
    public CourseProfessorStudentListResponse toCourseProfessorList(Course course);

    //For the basic course with no professor or students
    //No need for any mapping, they are all named the same within the course and DTO
    public CourseResponse toBasicCourse(Course course);

    // Helper method for MapStruct to use when mapping the list
    default List<String> mapStudentCoursesToNames(List<StudentCourse> studentCourses) {
        if (studentCourses == null) {
            return null;
        }
        return studentCourses.stream()
                .map(sc -> sc.getStudent().getName())
                .toList();
    }
}
