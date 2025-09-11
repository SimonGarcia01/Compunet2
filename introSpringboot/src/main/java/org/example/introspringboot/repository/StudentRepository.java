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

    //This method is used in the test
    //1. Workshop - find a student by id
    Optional<Student> findByCode(String code);

    //4. Workshop - Find all the students within an academic program
    List<Student> findByProgramEquals(String program);

    //7. Workshop - find all students of a specific program which code starts with a determined value
    List<Student> findByProgramEqualsAndCodeStartsWith(String program, String codeStart);

    //8. Workshop - find all the students attending a course with a specific professor (by name).
    // Student -> StudentCourse -> Course -> Professor
    List<Student> findByStudentCourses_Course_Professor_NameEquals(String professorName);
}
