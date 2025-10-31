package org.example.introspringboot.service.impl;

import org.example.introspringboot.api.v1.dto.*;
import org.example.introspringboot.api.v1.mappers.CourseMapper;
import org.example.introspringboot.api.v1.mappers.StudentMapper;
import org.example.introspringboot.entity.Course;
import org.example.introspringboot.repository.CourseRepository;
import org.example.introspringboot.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public List<CourseProfessorResponse> findAll() {
        return courseRepository.findAll().stream().map(
                course -> courseMapper.toDto(course)
        ).toList();
    }

    //Spring consideres this so it added the map method to the Page data thing too
    //So you only need to map every elemento through it and you get a page back
    @Override
    public Page<CourseProfessorResponse> findAll(Pageable pageable) {
        return courseRepository.findAll(pageable)
                .map(course -> courseMapper.toDto(course));
    }

    @Override
    public Optional<CourseProfessorResponse> findById(Integer id) {
        return courseRepository.findById(id).map(courseMapper::toDto);
    }

    @Transactional(readOnly = true)
    @Override
    public CourseProfessorStudentListResponse findAllWithStudents(Integer id) {
        //Find the Course by id
        return courseRepository.findById(id)
                .map(course -> {
                    CourseProfessorStudentListResponse dto =
                            courseMapper.toCourseProfessorList(course);

                    List<StudentDTO> studentList = course.getStudentCourses().stream()
                            .map(studentCourse ->
                                    studentMapper.toDto(studentCourse.getStudent())
                            )
                            .toList();

                    dto.setStudentDTOs(studentList);
                    return dto;
                }).orElse(null);
    }

    @Override
    public Course save(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public void deleteById(Integer id) {
        courseRepository.deleteById(id);
    }

    @Override
    public Page<CourseResponse> getCourseWName(String name, Pageable pageable) {
        return courseRepository.findByNameContainingIgnoreCase(name,pageable).map(courseMapper::toBasicCourse);
    }

    @Override
    public CourseOnlyStudentsResponse getCourseStudents(Integer id) {
        Course course = courseRepository.findById(id).orElse(null);

        List<StudentDTO> students = course.getStudentCourses().stream().map(
            studentCourse -> studentMapper.toDto(studentCourse.getStudent())
        ).toList();

        CourseOnlyStudentsResponse response = new CourseOnlyStudentsResponse();

        response.setStudents(students);

        return response;
    }

    @Override
    public List<CourseEnrollmentResponse> getCoursesEnrollment() {
        return courseRepository.findAll().stream().map(
            course -> {
                var courseEnrollment = courseMapper.toCourseEnrollment(course);
                courseEnrollment.setEnrolledStudents(course.getStudentCourses().size());
                return courseEnrollment;
            }
        ).toList();
    }

}
