package org.example.introspringboot.repository;

import org.example.introspringboot.entity.StudentCourse;
import org.example.introspringboot.entity.StudentCourseId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentCourseRepository extends JpaRepository<StudentCourse, StudentCourseId> {
}
