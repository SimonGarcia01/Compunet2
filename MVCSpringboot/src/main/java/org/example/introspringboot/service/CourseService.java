package org.example.introspringboot.service;

import org.example.introspringboot.entity.Course;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    List<Course> findAll();
    Optional<Course> findById(Integer id);
    Course save(Course course);
    void deleteById(Integer id);
}
