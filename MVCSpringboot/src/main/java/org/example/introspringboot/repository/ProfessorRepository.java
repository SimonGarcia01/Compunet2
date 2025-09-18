package org.example.introspringboot.repository;

import org.example.introspringboot.entity.Professor;
import org.example.introspringboot.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository<Professor, Integer> {
}
