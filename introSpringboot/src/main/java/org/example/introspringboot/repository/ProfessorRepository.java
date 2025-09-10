package org.example.introspringboot.repository;

import org.example.introspringboot.entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfessorRepository extends JpaRepository<Professor,Integer> {

    //Find the professors which names contain a text, ignoring case
    List<Professor> findByNameContainingIgnoreCase(String name);
}
