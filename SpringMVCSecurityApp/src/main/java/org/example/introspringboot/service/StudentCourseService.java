package org.example.introspringboot.service;

import org.example.introspringboot.entity.StudentCourse;
import org.example.introspringboot.entity.StudentCourseId;

import java.util.List;
import java.util.Optional;

public interface StudentCourseService {
    List<StudentCourse> findAll();
    Optional<StudentCourse> findById(StudentCourseId id);
    StudentCourse save(StudentCourse studentCourse);
    void deleteById(StudentCourseId id);
}
