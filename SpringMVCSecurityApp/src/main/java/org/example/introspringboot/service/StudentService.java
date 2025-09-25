package org.example.introspringboot.service;

import org.example.introspringboot.entity.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    long getCount();

    List<Student> getStudents();

    Optional<Student> findStudentByCode(String code);

    List<Student> getStudentsByCourseName(String name);

    Optional<Student> findById(Integer id);

    Student save(Student student);

    void deleteById(Integer id);
}
