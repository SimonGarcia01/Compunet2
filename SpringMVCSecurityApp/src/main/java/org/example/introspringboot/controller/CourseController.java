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
        model.addAttribute("courseList", courseList);
        model.addAttribute("course", new Course());
        model.addAttribute("professorList", professorService.findAll());
        return "course/course-list";
    }
    @PostMapping("/save")
    //Asi recibimos los cascarones llenados
    public String saveStudent(@ModelAttribute Course course) {
        courseService.save(course);
        return "redirect:/courses/";
    }

    @GetMapping("/{id}")
    public String courseDetail(@PathVariable("id") Integer id, Model model) {
        Optional<Course> course = courseService.findById(id);
        if(course.isPresent()){
            model.addAttribute("course", course.get());
            return "course/course-detail";
        }else{
            return "error/404";
        }

    }

}
