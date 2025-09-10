package org.example.introspringboot.controller;

import org.example.introspringboot.entity.Professor;
import org.example.introspringboot.entity.Student;
import org.example.introspringboot.repository.CourseRepository;
import org.example.introspringboot.repository.ProfessorRepository;
import org.example.introspringboot.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class ControllerWorkshop {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    ProfessorRepository professorRepository;

    @Autowired
    CourseRepository courseRepository;

    @GetMapping("1")
    public ResponseEntity<?> ws1(){
        Optional<Student> output = studentRepository.findByCode("2021102001");
        return ResponseEntity.status(200).body(output);
    }

    @GetMapping("2")
    public ResponseEntity<?> ws2(){
        List<Professor> output = professorRepository.findByNameContainingIgnoreCase("g");
        return ResponseEntity.status(200).body(output);
    }

    @GetMapping("c1")
    public ResponseEntity<?> class1(){
        var out = courseRepository.findFirstByOrderByNameAsc();
        return ResponseEntity.status(200).body(out);
    }

    @GetMapping("c2")
    public ResponseEntity<?> class2(){
        var out = courseRepository.findTop2ByOrderByCreditsDesc();
        return ResponseEntity.status(200).body(out);
    }

    @GetMapping("c3")
    public ResponseEntity<?> class3(){
        var out = courseRepository.findFirst1ByProfessor_Name("Juan Perez");
        return ResponseEntity.status(200).body(out);
    }

    @GetMapping("c5")
    public ResponseEntity<?> class5(){
        var out = courseRepository.findByProfessor_NameOrderByNameDesc("Juan Perez");
        return ResponseEntity.status(200).body(out);
    }

    @GetMapping("page")
    public ResponseEntity<?> classPage(){
        var out = courseRepository.findByCreditsGreaterThanEqual(0,
                PageRequest.of(0, 4));
        return ResponseEntity.status(200).body(out);
    }

}
