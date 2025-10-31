package org.example.introspringboot.api.v1;

import org.example.introspringboot.api.v1.dto.StudentDTO;
import org.example.introspringboot.api.v1.dto.StudentOnlyCoursesResponse;
import org.example.introspringboot.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    //REST E6.
    @GetMapping("")
    @PreAuthorize("hasRole('PROFESSOR')")
    public ResponseEntity<?> getStudentsByProgram(@RequestParam String program){
        Page<StudentDTO> students = studentService.getStudentsByProgram(program, PageRequest.of(0, 2));
        return ResponseEntity.status(200).body(students);
    }
}
