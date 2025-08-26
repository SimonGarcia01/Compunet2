package org.example.introspringboot.controller;

import org.example.introspringboot.entity.Student;
import org.example.introspringboot.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("count")
    public String getStudentCount(){
        long count = studentRepository.count();
        return "Estudiantes: " + count;
    }

    @GetMapping("students")
    public String getStudents(){
        StringBuilder output = new StringBuilder();
        for(Student student : studentRepository.findAll()){
            output.append(student.getName());
        }

        return output.toString();
    }

}
