package org.example.introspringboot.repository;

import org.example.introspringboot.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository  extends JpaRepository<Course, Integer> {
    //Find the courses by their exact name
    List<Course> findByName(String name);

    //find courses which name contains something specific
    List<Course> findByNameContainingIgnoreCase(String keyword);

    //Find courses with credits greater than some number
    List<Course> findByCreditsGreaterThan(int credits);

    //Find the courses that has a specific professor
    List<Course> findByProfessorName(String professorName);

    //Count the courses that a professor teaches
    long countByProfessorName(String professorName);

}
