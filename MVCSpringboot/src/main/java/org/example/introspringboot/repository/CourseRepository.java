package org.example.introspringboot.repository;

import org.example.introspringboot.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Integer> {

    Optional<Course> findFirstByOrderByNameAsc();

    List<Course> findTop2ByOrderByCreditsDesc();

    List<Course> findFirst100ByProfessor_Name(String professorName);


    List<Course> findByProfessor_NameOrderByNameDesc(String professorName);

    Page<Course> findByProfessor_Name(String professorName, Pageable pageable);

    Page<Course> findByCreditsGreaterThanEqual(int credits, Pageable pageable);


}
