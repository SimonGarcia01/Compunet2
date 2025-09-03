package org.example.introspringboot.services;

import org.example.introspringboot.entity.Student;

import java.util.List;

public interface StudentService {

    long getCount();

    List<Student> getStudents();

}