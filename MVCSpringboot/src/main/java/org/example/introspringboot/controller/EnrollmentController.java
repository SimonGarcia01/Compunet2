package org.example.introspringboot.controller;

import org.example.introspringboot.entity.Course;
import org.example.introspringboot.entity.Student;
import org.example.introspringboot.service.CourseService;
import org.example.introspringboot.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/enrollments")
public class EnrollmentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    @GetMapping("/")
    public String enrollStudent(Model model){
        List<Student> students = studentService.getStudents();
        List<Course> courses = courseService.findAll();

        model.addAttribute("students", students);
        model.addAttribute("courses", courses);

        return "enrollment/enrollment-page";
    }
}
