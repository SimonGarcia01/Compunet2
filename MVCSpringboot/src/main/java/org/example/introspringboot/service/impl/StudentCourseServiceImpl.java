package org.example.introspringboot.service.impl;

import org.example.introspringboot.entity.StudentCourse;
import org.example.introspringboot.entity.StudentCourseId;
import org.example.introspringboot.repository.StudentCourseRepository;
import org.example.introspringboot.service.StudentCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentCourseServiceImpl implements StudentCourseService {

    @Autowired
    private StudentCourseRepository studentCourseRepository;

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
}
