package org.example.introspringboot.service;

import org.example.introspringboot.api.v1.dto.CourseProfessorResponse;
import org.example.introspringboot.api.v1.dto.CourseProfessorStudentListResponse;
import org.example.introspringboot.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    List<CourseProfessorResponse> findAll();
    Page<CourseProfessorResponse> findAll(Pageable pageable);
    Optional<CourseProfessorResponse> findById(Integer id);
    CourseProfessorStudentListResponse getCourseProfessorListStudents(Integer id);
    Course save(Course course);
    void deleteById(Integer id);
}
