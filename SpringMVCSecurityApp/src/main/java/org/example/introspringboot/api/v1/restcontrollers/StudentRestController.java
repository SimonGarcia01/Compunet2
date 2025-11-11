package org.example.introspringboot.api.v1.restcontrollers;

import org.example.introspringboot.api.v1.dto.MessageResponse;
import org.example.introspringboot.api.v1.dto.StudentDTO;
import org.example.introspringboot.api.v1.dto.StudentOnlyCoursesResponse;
import org.example.introspringboot.entity.Student;
import org.example.introspringboot.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:5173"})
@RestController
@RequestMapping("/api/v1/students")
public class StudentRestController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('PROFESSOR')")
    public ResponseEntity<?> getStudentById(@PathVariable String id) {
        //I know I should respond with a DTO but I will just use the complete entity
        Student student = studentService.findStudentByCode(id).orElse(null);
        return ResponseEntity.status(200).body(student);
    }

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
    public ResponseEntity<?> getStudents(
            @RequestParam(required = false) String program,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        // No filter → return ALL students (List)
        if (program == null || program.isBlank()) {
            List<StudentDTO> students = studentService.getAllStudents();
            return ResponseEntity.ok(students);
        }

        // With filter → return paged students
        Page<StudentDTO> students = studentService.getStudentsByProgram(program, PageRequest.of(page, size));
        return ResponseEntity.ok(students);
    }

    //REST E9.
    @PostMapping("")
    @PreAuthorize("hasAuthority('ROLE_PROFESSOR')")
    public ResponseEntity<?> addStudent(@RequestBody StudentDTO studentDTO) {
        studentService.createStudent(studentDTO);
        return ResponseEntity.status(200).body(new MessageResponse("Student Created Successfully"));
    }

    //REST E11.
    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable Integer id, @RequestBody StudentDTO request) {
        studentService.updateStudent(id, request);
        return ResponseEntity.status(200).body(new MessageResponse("Student Updated Successfully"));
    }
}
