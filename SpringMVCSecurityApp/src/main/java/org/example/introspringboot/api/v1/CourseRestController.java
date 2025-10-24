package org.example.introspringboot.api.v1;

import org.example.introspringboot.api.v1.dto.CourseProfessorResponse;
import org.example.introspringboot.api.v1.dto.CourseProfessorStudentListResponse;
import org.example.introspringboot.api.v1.dto.CourseResponse;
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
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/api/v1/courses")
public class CourseRestController {

    @Autowired
    private CourseService courseService;

    //REST E1 y E3.
    //Removed the / from the get so the request param is just ?name=X, not /?=name=X
    @GetMapping("")
    @PreAuthorize("hasAuthority('ROLE_PROFESSOR')")
    public ResponseEntity<?> getCourses(@RequestParam(required = false) String name) {
        PageRequest pageable = PageRequest.of(0, 4);

        if (name != null && !name.isBlank()) {
            Page<CourseResponse> response = courseService.getCourseWName(name, pageable);
            return ResponseEntity.ok(response);
        }

        Page<CourseProfessorResponse> response = courseService.findAll(pageable);
        return ResponseEntity.ok(response);
    }

    //Original REST E3 but there is a problem with the get mapping with the same name.
    //So I commented this one and merged the two
    //Look with /courses/name?=X
//    @GetMapping("/")
//    @PreAuthorize("hasAuthority('ROLE_PROFESSOR')")
//    public ResponseEntity<?> getCoursesByName(@RequestParam String name){
//        Page<CourseResponse> response = courseService.getCourseWName(name, PageRequest.of(0, 4));
//        return ResponseEntity.status(200).body(response);
//

    //REST E2.
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('PROFESSOR')")
    public ResponseEntity<?> getCourseById(@PathVariable("id") Integer id){
        CourseProfessorStudentListResponse response = courseService.findAllWithStudents(id);
        return ResponseEntity.status(200).body(response);
    }
}
