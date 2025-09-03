package org.example.introspringboot.controller;

import org.example.introspringboot.entity.Student;
import org.example.introspringboot.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {

    @Autowired
    @Qualifier("studentServiceImpl")
    private StudentService studentService;

    @GetMapping("count")
    public String getStudentCount(){
        long count = studentService.getCount();
        return "Estudiantes: " + count;
    }

    @GetMapping("students")
    public String getStudents(){
        StringBuilder output = new StringBuilder();
        for(Student student : studentService.getStudents()){
            output.append(student.getName());
        }

        return output.toString();
    }

}
