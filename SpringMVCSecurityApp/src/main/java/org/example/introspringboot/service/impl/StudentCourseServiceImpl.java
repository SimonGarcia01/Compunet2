package org.example.introspringboot.service.impl;

import org.example.introspringboot.api.v1.dto.StudentCourseRequest;
import org.example.introspringboot.entity.Course;
import org.example.introspringboot.entity.Student;
import org.example.introspringboot.entity.StudentCourse;
import org.example.introspringboot.entity.StudentCourseId;
import org.example.introspringboot.repository.CourseRepository;
import org.example.introspringboot.repository.StudentCourseRepository;
import org.example.introspringboot.repository.StudentRepository;
import org.example.introspringboot.service.StudentCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentCourseServiceImpl implements StudentCourseService {

    @Autowired
    private StudentCourseRepository studentCourseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public List<StudentCourse> findAll() {
        return studentCourseRepository.findAll();
    }

    @Override
    public Optional<StudentCourse> findById(StudentCourseId id) {
        return studentCourseRepository.findById(id);
    }

    @Override
    public StudentCourse save(StudentCourse studentCourse) {
        return studentCourseRepository.save(studentCourse);
    }

    @Override
    public void deleteById(StudentCourseId id) {
        studentCourseRepository.deleteById(id);
    }

    @Override
    public void createEnrollment(StudentCourseRequest studentCourseRequest) {
        Course course = courseRepository.findById(studentCourseRequest.getCourseId()).orElse(null);
        Student student = studentRepository.findById(studentCourseRequest.getStudentId()).orElse(null);

        StudentCourseId id = new StudentCourseId(course.getId(), student.getId());

        StudentCourse studentCourse = new StudentCourse(id,  course, student);

        studentCourseRepository.save(studentCourse);
    }

    @Override
    public void deleteEnrollment(Integer studentId, Integer courseId) {
        StudentCourseId id = new StudentCourseId(studentId, courseId);
        studentCourseRepository.deleteById(id);
    }
}
