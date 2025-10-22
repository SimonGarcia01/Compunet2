package org.example.introspringboot.service.impl;

import org.example.introspringboot.api.v1.dto.CourseProfessorResponse;
import org.example.introspringboot.api.v1.dto.CourseProfessorStudentListResponse;
import org.example.introspringboot.api.v1.mappers.CourseMapper;
import org.example.introspringboot.entity.Course;
import org.example.introspringboot.repository.CourseRepository;
import org.example.introspringboot.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseMapper courseMapper;

    @Override
    public List<CourseProfessorResponse> findAll() {
        return courseRepository.findAll().stream().map(
                course -> courseMapper.toDto(course)
        ).toList();
    }

    //Spring consideres this so it added the map method to the Page data thing too
    //So you only need to map every elemento through it and you get a page back
    @Override
    public Page<CourseProfessorResponse> findAll(Pageable pageable) {
        return courseRepository.findAll(pageable)
                .map(course -> courseMapper.toDto(course));
    }

    @Override
    public Optional<CourseProfessorResponse> findById(Integer id) {
        return courseRepository.findById(id).map(courseMapper::toDto);
    }

    @Override
    public CourseProfessorStudentListResponse getCourseProfessorListStudents(Integer id) {
        return courseRepository.findById(id).map(courseMapper::toCourseProfessorList).orElse(null);
    }

    @Override
    public Course save(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public void deleteById(Integer id) {
        courseRepository.deleteById(id);
    }
}
