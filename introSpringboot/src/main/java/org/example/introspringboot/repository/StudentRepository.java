package org.example.introspringboot.repository;

import org.example.introspringboot.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    //find students that belong to a course using the course name
    List<Student> findByStudentCourses_Course_Name(String courseName);

    //find a student by id
    Optional<Student> findByCode(String code);


}
