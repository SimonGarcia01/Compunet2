package org.example.introspringboot.repository;

import org.example.introspringboot.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    Optional<Student> findByCode(String code);

    List<Student> findByStudentCourses_Course_Name(String name);

    Page<Student> findByProgramOrderByIdAsc(String program, Pageable pageable);
}

