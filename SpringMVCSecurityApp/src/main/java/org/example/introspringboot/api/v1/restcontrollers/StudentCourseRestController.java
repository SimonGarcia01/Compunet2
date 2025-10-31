package org.example.introspringboot.api.v1.restcontrollers;

import org.example.introspringboot.api.v1.dto.StudentCourseRequest;
import org.example.introspringboot.api.v1.dto.MessageResponse;
import org.example.introspringboot.service.StudentCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/enrollments")
public class StudentCourseRestController {

    @Autowired
    private StudentCourseService studentCourseService;

    //REST #10.
    @PostMapping("")
    @PreAuthorize("hasRole('PROFESSOR')")
    public ResponseEntity<?> createEnrollment(@RequestBody StudentCourseRequest studentCourseRequest) {
        studentCourseService.createEnrollment(studentCourseRequest);
        return ResponseEntity.status(200).body(new MessageResponse("The student has been enrolled to the entered course."));
    }

    //REST 12.
    @DeleteMapping("/{studentId}/{courseId}")
    @PreAuthorize("hasRole('PROFESSOR')")
    public ResponseEntity<?> deleteEnrollment(@PathVariable("studentId") Integer studentId,
                                              @PathVariable("courseId") Integer courseId) {
        studentCourseService.deleteEnrollment(studentId, courseId);
        return ResponseEntity.status(200).body(new MessageResponse("The student has been deleted from the entered course."));
    }
}
