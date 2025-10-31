package org.example.introspringboot;

import org.example.introspringboot.api.v1.dto.StudentOnlyCoursesResponse;
import org.example.introspringboot.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/students")
public class StudentRestController {

    @Autowired
    private StudentService studentService;

    //REST E5.
    @GetMapping("/{id}/courses")
    @PreAuthorize("hasRole('PROFESSOR')")
    public ResponseEntity<?> getCoursesOfStudent(@PathVariable("id") Integer id) {
        StudentOnlyCoursesResponse courses = studentService.getCoursesStudentId(id);
        return ResponseEntity.status(200).body(courses);
    }
}
