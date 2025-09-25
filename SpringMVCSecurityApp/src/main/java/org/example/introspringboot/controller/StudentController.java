package org.example.introspringboot.controller;


import org.example.introspringboot.entity.Student;
import org.example.introspringboot.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/")
    public String getStudents(Model model) {
        List<Student> studentsList = studentService.getStudents();
        model.addAttribute("studentsList", studentsList);

        Student student = new Student();
        model.addAttribute("student", student);

        return "student/student-list";
    }
    @PostMapping("/save")
    //Asi recibimos los cascarones llenados
    public String saveStudent(@ModelAttribute Student student) {
        studentService.save(student);
        return "redirect:/students/";
    }

}
