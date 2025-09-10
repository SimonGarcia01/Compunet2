package org.example.introspringboot.repository;

import org.example.introspringboot.entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfessorRepository extends JpaRepository<Professor,Integer> {

    //2. Workshop - Find the professors which names contain a text, ignoring case
    List<Professor> findByNameContainingIgnoreCase(String name);

    //10. Workshop - Find all professors (without duplicates) that teach students from a specific academic program
    // Navigate Professors -> Course -> StudentCourse -> Student
    List<Professor> findDistinctByCourses_StudentCourses_Student_ProgramEquals(String studentProgram);
}
