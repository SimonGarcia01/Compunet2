package org.example.introspringboot.services;

import org.example.introspringboot.entity.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    long getCount();

    List<Student> getStudents();

    //Method used in the test to find a student by code
    Optional<Student> findStudentByCode(String code);

    List<Student> getStudentsByCourseName(String name);
}