package org.example.introspringboot.controller;

import org.example.introspringboot.entity.Course;
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

    @GetMapping("3")
    public ResponseEntity<?> ws3(){
        List<Course> output = courseRepository.findByCreditsEquals(3);
        return ResponseEntity.status(200).body(output);
    }

    @GetMapping("4")
    public ResponseEntity<?> ws4(){
        List<Student> output = studentRepository.findByProgramEquals("Ingenieria de Sistemas");
        return ResponseEntity.status(200).body(output);
    }

    @GetMapping("5")
    public ResponseEntity<?> ws5(){
        Optional<Course> output = courseRepository.findByNameEqualsIgnoreCase("derecho penal");
        return ResponseEntity.status(200).body(output);
    }

    @GetMapping("6")
    public ResponseEntity<?> ws6(){
        List<Course> output = courseRepository.findByProfessor_NameEqualsOrderByNameAsc("Carlos Gomez");
        return ResponseEntity.status(200).body(output);
    }

    @GetMapping("7")
    public ResponseEntity<?> ws7(){
        List<Student> output = studentRepository.findByProgramEqualsAndCodeStartsWith("Ingenieria de Sistemas", "30");
        return ResponseEntity.status(200).body(output);
    }

    @GetMapping("8")
    public ResponseEntity<?> ws8(){
        List<Course> output = courseRepository.findByCreditsBetween(2,3);
        return ResponseEntity.status(200).body(output);
    }

    @GetMapping("9")
    public ResponseEntity<?> ws9(){
        List<Student> output = studentRepository.findByStudentCourses_Course_Professor_NameEquals("Maria Rodriguez");
        return ResponseEntity.status(200).body(output);
    }

    @GetMapping("10")
    public ResponseEntity<?> ws10(){
        List<Professor> output = professorRepository.findDistinctByCourses_StudentCourses_Student_ProgramEquals("Ingenieria de Sistemas");
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
