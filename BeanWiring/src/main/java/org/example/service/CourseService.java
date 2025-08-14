package org.example.service;

import org.example.repository.CourseRepository;
import org.example.repository.StudentRepository;

public class CourseService {

    private CourseRepository courseRepository;
    private StudentRepository studentRepository;

    public CourseService(CourseRepository courseRepository, StudentRepository studentRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }
}
