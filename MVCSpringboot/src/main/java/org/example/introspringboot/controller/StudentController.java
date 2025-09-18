package org.example.introspringboot.controller;

import org.example.introspringboot.entity.Student;
import org.example.introspringboot.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    @Qualifier("studentServiceImpl")
    private StudentService studentService;

    @GetMapping("/")
    public String getStudents(Model model){
        List<Student> students = studentService.getStudents();
        model.addAttribute("studentList", students);

        //For a form you need to send the empty shell so it can be filled
        Student student = new Student();
        model.addAttribute("student", student);

        return "student/student-list";
    }

    @PostMapping("/save")
    public String saveStudent(@ModelAttribute Student student){ //Receive the eggshell filled
        studentService.save(student);
        return "redirect:/students/";
    }


}
