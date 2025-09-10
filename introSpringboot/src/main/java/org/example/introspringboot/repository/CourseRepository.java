package org.example.introspringboot.repository;

import org.example.introspringboot.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

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

    //return the first element ordered by name in ascending order
    Optional<Course> findFirstByOrderByNameAsc();

    //return the top 2 courses ordered descending by credits
    List<Course> findTop2ByOrderByCreditsDesc();

    //Return the 3 courses from a specific professor
    List<Course> findFirst1ByProfessor_Name(String professorName);

    //Find the courses of a professor name his courses which are then ordered by name
    List<Course> findByProfessor_NameOrderByNameDesc(String professorName);

    //Find all courses with a certain number of credits and then order all courses by name
    List<Course> findByCreditsOrderByNameAsc(int credits);

    //return the courses of a professor in pages
    Page<Course> findByProfessor_Name(String professorName, Pageable pageable);

    //Return the courses that have credits greater than some number
    Page<Course> findByCreditsGreaterThanEqual(int credits, Pageable pageable);

    //3. Workshop - Find all the courses with a specific number of credits
    List<Course> findByCreditsEquals(int credits);

    //5. Workshop - Find a course by its exact name not case-sensitive
    Optional<Course> findByNameEqualsIgnoreCase(String name);

    //6. Workshop - Find all the courses given by a specific professor (by name) and order it alphabetically
    List<Course> findByProfessor_NameEqualsOrderByNameAsc(String professorName);

    //8. Workshop - Find all courses which the number of credits are between a range
    List<Course> findByCreditsBetween(int minCredits, int maxCredits);
}
