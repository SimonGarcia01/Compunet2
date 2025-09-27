package org.example.introspringboot.controller;

import org.example.introspringboot.entity.Course;
import org.example.introspringboot.entity.Student;
import org.example.introspringboot.entity.StudentCourse;
import org.example.introspringboot.entity.StudentCourseId;
import org.example.introspringboot.service.CourseService;
import org.example.introspringboot.service.StudentCourseService;
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
@RequestMapping("/enrollments")
public class EnrollmentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private StudentCourseService studentCourseService;

    @GetMapping("/")
    public String enrollStudent(Model model){
        List<Student> students = studentService.getStudents();
        List<Course> courses = courseService.findAll();

        //Add the list of students to the template
        model.addAttribute("students", students);
        //Add the list of courses to the template
        model.addAttribute("courses", courses);

        //Send an empty eggshell to later be filled with the form
        model.addAttribute("studentCourse", new StudentCourse());

        //To see all the linked courses and students
        model.addAttribute("enrollments", studentCourseService.findAll());

        return "enrollment/enrollment-page";
    }

    @PostMapping("/save")
    public String saveEnrollment(@ModelAttribute StudentCourse studentCourse){
        //Only the ID is set for now so not i must set the Student and Course
        //Get both the student and course from the eggshell info
        Integer studentId = studentCourse.getStudentCourseId().getStudentId();
        Integer courseId = studentCourse.getStudentCourseId().getCourseId();

        //Find the student and the course
        Student student = studentService.findById(studentId).orElse(null);
        Course  course  = courseService.findById(courseId).orElse(null);


        //Set both the student and course
        studentCourse.setStudent(student);
        studentCourse.setCourse(course);

        //Now actually save it
        studentCourseService.save(studentCourse);
        return "redirect:/enrollments/";
    }
}
