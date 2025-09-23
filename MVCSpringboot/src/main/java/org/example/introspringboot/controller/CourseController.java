package org.example.introspringboot.controller;


import org.example.introspringboot.entity.Course;
import org.example.introspringboot.entity.Professor;
import org.example.introspringboot.entity.Student;
import org.example.introspringboot.service.CourseService;
import org.example.introspringboot.service.ProfessorService;
import org.example.introspringboot.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("courses")
public class CourseController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private ProfessorService professorService;

    @GetMapping("/")
    public String getStudents(Model model) {
        List<Course> courseList = courseService.findAll();
        //This returns all the courses
        model.addAttribute("courseList", courseList);

        //Attributes for the new course
        model.addAttribute("course", new Course());

        //Get all the professors for the <select>
        model.addAttribute("professorList", professorService.findAll());
        return "course/course-list";
    }
    @PostMapping("/save")
    //Here we receive the eggShells with the info
    public String saveStudent(@ModelAttribute Course course) {
        courseService.save(course);
        return "redirect:/courses/";
    }

    @GetMapping("/{id}")
    public String courseDetail(@PathVariable("id") Integer id, Model model) {
        //First fin the course using the ID
        Optional<Course> course = courseService.findById(id);

        //Then check if the course actually exists, then send it to the template
        course.ifPresent(value -> model.addAttribute("course", value));
        //This is the same that is not in a functional version
//        if(course.isPresent()){
//            model.addAttribute("course", course.get());
//        }
        return "course/course-detail";
    }

}
