package org.example.introspringboot.api.v1;

import org.example.introspringboot.api.v1.dto.CourseProfessorResponse;
import org.example.introspringboot.api.v1.dto.CourseProfessorStudentListResponse;
import org.example.introspringboot.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/v1/courses")
public class CourseRestController {

    @Autowired
    private CourseService courseService;

    //REST E1.
    @GetMapping("/")
    @PreAuthorize("hasAuthority('ROLE_PROFESSOR')")
    public ResponseEntity<?> getCourses(){
        Page<CourseProfessorResponse> response = courseService.findAll(PageRequest.of(0, 4));
        return ResponseEntity.status(200).body(response);
    }

    //REST E2.
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('PROFESSOR')")
    public ResponseEntity<?> getCourseById(@PathVariable("id") Integer id){
        CourseProfessorStudentListResponse response = courseService.getCourseProfessorListStudents(id);
        return ResponseEntity.status(200).body(response);
    }
}
