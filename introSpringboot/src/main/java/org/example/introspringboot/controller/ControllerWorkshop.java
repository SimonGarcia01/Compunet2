package org.example.introspringboot.controller;

import org.example.introspringboot.entity.Student;
import org.example.introspringboot.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class ControllerWorkshop {

    @Autowired
    StudentRepository studentRepository;

    @GetMapping("1")
    public ResponseEntity<?> exercise1(){
        Optional<Student> output = studentRepository.findByCode("2021102001");
        return ResponseEntity.status(200).body(output);
    }

}
